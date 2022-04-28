#include <iostream>
#include <string>
using namespace std;

/*
* Задача 77
*
* Да се състави програма на С/С++, която използва функция за търсене на всички преки
* наследници на определен елемент в дадено двоично дърво за търсене.
*/

struct Element {
    int value;
    Element* left;
    Element* right;
};

int numbers_to_add[] = { 10, -3, -2, -1, 0, 1, 2, 3, 4, 5, 11 };

bool add(Element*& node, int value_to_add);
void fill_in_tree(Element*& node);
bool print_preorder(Element* node);
Element* search_for_value(Element* node, int searched_value);

int main()
{

    Element* root = NULL;
    fill_in_tree(root);

    printf("Print INORDER : ");
    print_preorder(root);

    int search_value;
    printf("\n\nEnter search value: ");
    cin >> search_value;

    Element* found_value = search_for_value(root, search_value);
    if (!found_value) printf("\nElement %d does not exist!\n", search_value);
    else
    {
        printf("\nSuccessors: Left: %s, Right: %s\n",
            found_value->left ? to_string(found_value->left->value).c_str() : "NULL",
            found_value->right ? to_string(found_value->right->value).c_str() : "NULL"
        );
    }
}

bool add(Element*& node, int value_to_add)
{
    if (!node)
    {
        node = new Element;
        node->value = value_to_add;
        node->left = NULL;
        node->right = NULL;
    }
    else
    {
        if (node->value < value_to_add) add(node->right, value_to_add);
        else if (node->value > value_to_add) add(node->left, value_to_add);
        else return false;
    }

    return true;
}

void fill_in_tree(Element*& node)
{
    for (int i = 0; i < *(&numbers_to_add + 1) - numbers_to_add; i++)
    {
        add(node, numbers_to_add[i]);
    }
}

bool print_preorder(Element* node)
{
    if (!node) return false;

    printf("%d ", node->value);
    print_preorder(node->left);
    print_preorder(node->right);

    return true;
}

Element* search_for_value(Element* node, int searched_value)
{
    while (node && node->value != searched_value)
    {
        if (node->value < searched_value) node = node->right;
        else node = node->left;
    }

    return node;
}
