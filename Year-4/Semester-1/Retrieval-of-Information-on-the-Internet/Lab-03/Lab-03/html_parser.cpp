#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <cctype>
#include <cstring>
#include <stack>
#include <map>
#include <vector>
#include <regex>
#include <algorithm>

// Global reverse index
std::map<std::string, std::vector<int>> reverseIndex;

// Helper function to trim whitespace from the start and end of a string
std::string trim(const std::string& str) {
    size_t start = str.find_first_not_of(" \n\r\t\f\v");
    if (start == std::string::npos) return ""; // If no non-space characters
    size_t end = str.find_last_not_of(" \n\r\t\f\v");
    return str.substr(start, end - start + 1);
}

void parseHTML(const std::string& html) {
    size_t pos = 0;
    std::string tagName, textContent;
    std::map<std::string, std::string> attributes;
    std::stack<std::string> tagStack;
    std::string textInsideATag; // To store text inside <a> tag
    bool insideATag = false; // Flag to know if we're inside an <a> tag

    auto skipSpaces = [&]() {
        while (pos < html.length() && std::isspace(html[pos])) {
            ++pos;
        }
    };

    auto parseTagName = [&]() -> std::string {
        std::string name;
        while (pos < html.length() && std::isalnum(html[pos])) {
            name += html[pos++];
        }
        return name;
    };

    auto parseAttributes = [&]() -> std::map<std::string, std::string> {
        std::map<std::string, std::string> attrs;
        while (pos < html.length() && html[pos] != '>' && html[pos] != '/') {
            skipSpaces();
            std::string attrName, attrValue;

            // Parse attribute name
            while (pos < html.length() && std::isalnum(html[pos])) {
                attrName += html[pos++];
            }

            skipSpaces();
            if (pos < html.length() && html[pos] == '=') {
                ++pos; // Skip '='
                skipSpaces();

                // Parse attribute value
                if (pos < html.length() && html[pos] == '"') {
                    ++pos; // Skip starting quote
                    while (pos < html.length() && html[pos] != '"') {
                        attrValue += html[pos++];
                    }
                    ++pos; // Skip ending quote
                } else {
                    // No quotes
                    while (pos < html.length() && !std::isspace(html[pos]) && html[pos] != '>') {
                        attrValue += html[pos++];
                    }
                }
            }

            if (!attrName.empty()) {
                attrs[attrName] = attrValue;
            }

            skipSpaces();
        }
        return attrs;
    };

    auto parseTextContent = [&]() -> std::string {
        std::string text;
        while (pos < html.length() && html[pos] != '<') {
            text += html[pos++];
        }
        return text;
    };

    while (pos < html.length()) {
        if (html[pos] == '<') {
            ++pos; // Skip '<'

            if (html[pos] == '/') {
                // Closing tag
                ++pos;
                tagName = parseTagName();
                std::cout << "Closing tag: </" << tagName << ">\n";

                if (tagStack.empty() || tagStack.top() != tagName) {
                    std::cerr << "Error: Unexpected closing tag </" << tagName << ">!\n";
                    return;
                }

                if (tagName == "a") {
                    insideATag = false; // We have exited the <a> tag
                }

                tagStack.pop();
                ++pos; // Skip '>'
            } else {
                // Opening tag
                tagName = parseTagName();
                std::cout << "Opening tag: <" << tagName << ">\n";

                attributes = parseAttributes();
                if (!attributes.empty()) {
                    std::cout << "Attributes:\n";
                    for (std::map<std::string, std::string>::iterator it = attributes.begin(); it != attributes.end(); ++it) {
                        std::cout << "  " << it->first << " = " << it->second << "\n";
                    }
                }

                if (html[pos] == '/') {
                    ++pos;
                } else {
                    tagStack.push(tagName); // Push tag to stack
                }

                if (tagName == "a") {
                    insideATag = true; // We have entered an <a> tag
                }

                ++pos; // Skip '>'
            }
        } else {
            // Text content
            textContent = parseTextContent();
            std::string trimmedText = trim(textContent);
            if (!trimmedText.empty()) {
                std::cout << "Text content: " << trimmedText << "\n";
                if (insideATag) {
                    textInsideATag += " " + trimmedText;
                }
            }
        }
    }

    if (!tagStack.empty()) {
        std::cerr << "Error: Unmatched opening tag <" << tagStack.top() << ">!\n";
    } else {
        std::cout << "\nText inside <a> tag: " << textInsideATag << "\n";

        std::regex wordRegex("\\w+");
        std::sregex_iterator words_begin(textInsideATag.begin(), textInsideATag.end(), wordRegex);
        std::sregex_iterator words_end;

        int position = 0;
        for (std::sregex_iterator i = words_begin; i != words_end; ++i) {
            std::string word = i->str();
            std::transform(word.begin(), word.end(), word.begin(), ::tolower);
            reverseIndex[word].push_back(position);
            position++;
        }

        std::cout << "\nReverse Index for <a> tag content:\n";
        for (std::map<std::string, std::vector<int>>::iterator it = reverseIndex.begin(); it != reverseIndex.end(); ++it) {
            std::cout << it->first << " -> ";
            for (size_t j = 0; j < it->second.size(); ++j) {
                std::cout << it->second[j] << (j < it->second.size() - 1 ? ", " : "");
            }
            std::cout << "\n";
        }
    }
}

bool containsWordInIndex(const std::string& word) {
    std::string lowerWord = word;
    std::transform(lowerWord.begin(), lowerWord.end(), lowerWord.begin(), ::tolower);
    return reverseIndex.find(lowerWord) != reverseIndex.end();
}

std::string readHTMLFromFile(const std::string& filename) {
    std::ifstream file(filename);
    if (!file.is_open()) {
        throw std::runtime_error("Could not open file: " + filename);
    }

    std::ostringstream buffer;
    buffer << file.rdbuf();
    return buffer.str();
}

int main() {
    try {
        std::string filename = "response.html";
        std::string html = readHTMLFromFile(filename);
        parseHTML(html);

        std::string inputWord;
        std::cout << "\nEnter a word to search for in the reverse index: ";
        std::cin >> inputWord;

        std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');

        if (containsWordInIndex(inputWord)) {
            std::cout << "The word \"" << inputWord << "\" is in the index.\n";
        } else {
            std::cout << "The word \"" << inputWord << "\" is NOT in the index.\n";
        }

        getchar();

    } catch (const std::exception& ex) {
        std::cerr << "Error: " << ex.what() << "\n";
        return 1;
    }
    return 0;
}
