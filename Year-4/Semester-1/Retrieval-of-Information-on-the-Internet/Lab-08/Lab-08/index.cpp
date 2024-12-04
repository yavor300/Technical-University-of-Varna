#include <iostream>
#include <vector>
#include <unordered_map>
#include <regex>
#include <string>
#include <cmath>

struct index_data {
    std::string word;
    std::vector<int> positions;
    double tf_idf;

    // Constructor for older C++ versions (pre-C++11)
    index_data() : word(""), positions(), tf_idf(0.0) {}
};

struct tree {
    index_data item;
    tree* parent;
    tree* left;
    tree* right;

    // Constructor to initialize pointers
    tree() : parent(nullptr), left(nullptr), right(nullptr) {}
};

void add_tree(tree*& t, const index_data& item, tree* parent = nullptr) {
    if (t == nullptr) {
        t = new tree();
        t->item = item;
        t->parent = parent;

        std::cout << "Added word: " << item.word << std::endl;
        return;
    }

    if (item.word < t->item.word) {
        add_tree(t->left, item, t);
    } else if (item.word > t->item.word) {
        add_tree(t->right, item, t);
    }
}

tree* search_tree(tree* t, const std::string& word) {
    if (t == nullptr) return nullptr;

    std::cout << "Searching for: " << word << " in tree node: " 
              << (t ? t->item.word : "nullptr") << std::endl;

    if (t->item.word == word) return t;
    return (word < t->item.word) ? search_tree(t->left, word) : search_tree(t->right, word);
}

void calculate_tf_idf(tree* root, int total_docs, const std::unordered_map<std::string, int>& doc_freq) {
    if (root == nullptr) return;

    auto it = doc_freq.find(root->item.word);
    if (it != doc_freq.end()) {
        double tf = static_cast<double>(root->item.positions.size());
        double idf = log(static_cast<double>(total_docs) / (1 + it->second));
        root->item.tf_idf = tf * idf;
    }

    calculate_tf_idf(root->left, total_docs, doc_freq);
    calculate_tf_idf(root->right, total_docs, doc_freq);
}

void print_inorder(tree* t) {
    if (t == nullptr) return;
    print_inorder(t->left);
    std::cout << "Word: " << t->item.word << ", Positions: ";
    for (int pos : t->item.positions) {
        std::cout << pos << " ";
    }
    std::cout << ", TF-IDF: " << t->item.tf_idf << "\n";
    print_inorder(t->right);
}

void free_tree(tree* t) {
    if (t == nullptr) return;
    free_tree(t->left);
    free_tree(t->right);
    delete t;
}

void build_inverted_index(const std::string& text, tree*& root, int total_docs) {
    std::regex word_regex("\\b\\w+\\b");
    auto words_begin = std::sregex_iterator(text.begin(), text.end(), word_regex);
    auto words_end = std::sregex_iterator();

    int position = 0;
    std::unordered_map<std::string, int> word_count;

    for (std::sregex_iterator i = words_begin; i != words_end; ++i) {
        std::string word = i->str();
        word_count[word]++;
        tree* found = search_tree(root, word);

        if (found == nullptr) {
            index_data new_data;
            new_data.word = word;
            new_data.positions.push_back(position);
            add_tree(root, new_data);
        } else {
            found->item.positions.push_back(position);
        }
        position++;
    }

    // Calculate TF-IDF values
    calculate_tf_idf(root, total_docs, word_count);
}

std::string extract_text_from_html(const std::string& html_content) {
    // Remove HTML tags
    std::regex tag_regex("<[^>]*>");
    return std::regex_replace(html_content, tag_regex, " ");
}

int main() {
    // Hardcoded HTML content
    std::string html_content = "<html>\n<body>\n    This is a test. This is an <b>HTML</b> example.\n</body>\n</html>";

    // Extract plain text from the hardcoded HTML
    std::string text = extract_text_from_html(html_content);
    std::cout << "Extracted Text: " << text << std::endl;

    tree* root = nullptr; // Initialize the root pointer
    int total_docs = 1;   // For a single document example

    std::cout << "Building inverted index...\n";
    build_inverted_index(text, root, total_docs);

    std::cout << "Inverted Index with TF-IDF:\n";
    print_inorder(root);

		getchar();

    // Free the tree to prevent memory leaks
    free_tree(root);

    return 0;
}
