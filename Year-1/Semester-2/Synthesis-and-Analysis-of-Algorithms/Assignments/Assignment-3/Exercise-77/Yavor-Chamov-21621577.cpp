#include <iostream>
#include <string>
using namespace std;

/*
* Задача 77
*
* Съставете програмаза работа с ориентиран граф, представен чрез списъци на съседство,
* която да предлага меню с основните операции със структурата граф.
* Да се напише функция, която да се добави към менютои съобщава,
* дали в даден ориентиран граф има върхове с две входящи дъги
*/
const int NUMBER_OF_NODES = 10;
int numbers_to_add[] = { 10, -3, -2, -1, 0, 1, 2, 3, 4, 5, 11 };

const int SEARCH_NODE_CHOICE = 1;
const int SEARCH_ARC_CHOICE = 2;
const int ADD_NODE_CHOICE = 3;
const int ADD_ARC_CHOICE = 4;
const int DELETE_NODE_CHOICE = 5;
const int DELETE_ARC_CHOICE = 6;
const int CHECK_FOR_INPUT_ARCS_CHOICE = 7;
const int EXIT_FROM_PROGRAM_CHOICE = 8;

struct Arc {
    char value;
    Arc* next;
};


void initialize(Arc* nodes[]);
int search_node(Arc* nodes[], char search_value);
bool search_arc(Arc* nodes[], char start_node, char end_node);
bool add_node(Arc* nodes[], char node_value);
bool add_arc(Arc* nodes[], char start_node, char end_node);
bool delete_node(Arc* nodes[], char node_value);
bool delete_arc(Arc* nodes[], char start_node, char end_node);
void check_input_arcs_count(Arc* nodes[]);

int main()
{
    Arc* nodes[NUMBER_OF_NODES];
    initialize(nodes);

    int menu_choice = 0;
    char start_node, end_node, node_value;

    do
    {
        printf("===== HOME MENU =====\n\nEnter %d to search for a node.\nEnter %d to search for an arc.\nEnter %d to add a node.\nEnter %d to add an arc.\nEnter %d to delete a node.\nEnter %d to delete an arc.\nEnter %d, to check if a node with 2 input arcs exists.\nEnter %d, to stop the program.\n",
           SEARCH_NODE_CHOICE, SEARCH_ARC_CHOICE, ADD_NODE_CHOICE, ADD_ARC_CHOICE, DELETE_NODE_CHOICE, DELETE_ARC_CHOICE, CHECK_FOR_INPUT_ARCS_CHOICE, EXIT_FROM_PROGRAM_CHOICE);
        do
        {
            printf("Enter valid menu option [%d - %d]: ", SEARCH_NODE_CHOICE, EXIT_FROM_PROGRAM_CHOICE);
            cin >> menu_choice;
        } while (menu_choice < SEARCH_NODE_CHOICE || menu_choice > EXIT_FROM_PROGRAM_CHOICE);

        system("cls");
        switch (menu_choice)
        {
        case SEARCH_NODE_CHOICE:
            char search_value;
            printf("Enter search value: ");
            cin >> search_value;

            if (search_node(nodes, search_value) > -1)
                printf("Node %c exists!\n", search_value);
            else
                printf("Node %c does not exists!\n", search_value);
            break;

        case SEARCH_ARC_CHOICE:
            printf("Enter start node: ");
            cin >> start_node;
            printf("Enter end node: ");
            cin >> end_node;

            if (search_arc(nodes, start_node, end_node))
                printf("Arc between %c and %c exists!\n", start_node, end_node);
            else
                printf("Arc between %c and %c does NOT exists!\n", start_node, end_node);
            break;

        case ADD_NODE_CHOICE:
            printf("Enter node value: ");
            cin >> node_value;

            if (add_node(nodes, node_value))
                printf("Node %c was added successfully!\n", node_value);
            else
                printf("Node %c was NOT added! Such node already exists or the structure is full!\n", node_value);
            break;

        case ADD_ARC_CHOICE:
            printf("Enter start node: ");
            cin >> start_node;
            printf("Enter end node: ");
            cin >> end_node;

            if (add_arc(nodes, start_node, end_node))
                printf("Arc with nodes %c, %c was added successfully!\n", start_node, end_node);
            else
                printf("Arc with nodes %c, %c was NOT added! Such arc might already exists.\n", start_node, end_node);
            break;

        case DELETE_NODE_CHOICE:
            printf("Enter node value: ");
            cin >> node_value;

            if (delete_node(nodes, node_value))
                printf("Node %c was deleted successfully!\n", node_value);
            else
                printf("Node %c was NOT deleted! Node does NOT exist!\n", node_value);
            break;

        case DELETE_ARC_CHOICE:
            printf("Enter start node: ");
            cin >> start_node;
            printf("Enter end node: ");
            cin >> end_node;

            if (delete_arc(nodes, start_node, end_node))
                printf("Arc with nodes %c, %c was deleted successfully!\n", start_node, end_node);
            else
                printf("Arc with nodes %c, %c was NOT deleted! Such arc might NOT exists.\n", start_node, end_node);
            break;

        case CHECK_FOR_INPUT_ARCS_CHOICE:
            check_input_arcs_count(nodes);
            break;
        }
        cout << endl;
    } while (menu_choice != EXIT_FROM_PROGRAM_CHOICE);
}

void initialize(Arc* nodes[])
{
    for (int i = 0; i < NUMBER_OF_NODES; i++) nodes[i] = NULL;
}

int search_node(Arc* nodes[], char search_value)
{
    for (int i = 0; i < NUMBER_OF_NODES; i++)
    {
        if (nodes[i] && nodes[i]->value == search_value) return i;
    }
    return -1;
}

bool search_arc(Arc* nodes[], char start_node, char end_node)
{
    int start_node_index = search_node(nodes, start_node);
    int end_node_index = search_node(nodes, end_node);

    if (start_node_index == -1 || end_node_index == -1) return false;

    Arc* beginning_node = nodes[start_node_index];

    if (start_node == end_node) {
        if (beginning_node->next) beginning_node = beginning_node->next;
        else return false;
    }

    while (beginning_node->value != end_node && beginning_node->next)
    {
        beginning_node = beginning_node->next;
    }

    if (beginning_node->value == end_node) return true;

    return false;
}

bool add_node(Arc* nodes[], char node_value)
{
    if (search_node(nodes, node_value) > -1) return false;

    int index = 0;
    while (nodes[index] && index < NUMBER_OF_NODES) index++;

    if (!nodes[index])
    {
        nodes[index] = new Arc;
        nodes[index]->value = node_value;
        nodes[index]->next = NULL;
        return true;
    }

    return false;
}

bool add_arc(Arc* nodes[], char start_node, char end_node)
{
    if (search_arc(nodes, start_node, end_node)) return false;

    if (search_node(nodes, start_node) == -1) add_node(nodes, start_node);
    if (search_node(nodes, end_node) == -1) add_node(nodes, end_node);

    int beginning_node_index = search_node(nodes, start_node);
    
    Arc* arc = new Arc;
    arc->value = end_node;
    arc->next = nodes[beginning_node_index]->next;
    nodes[beginning_node_index]->next = arc;

    return true;
}

bool delete_node(Arc* nodes[], char node_value)
{
    int node_to_delete_index = search_node(nodes, node_value);
    if (node_to_delete_index == -1) return false;

    Arc* node_to_delete = nodes[node_to_delete_index];

    while (nodes[node_to_delete_index])
    {
        node_to_delete = nodes[node_to_delete_index];
        nodes[node_to_delete_index] = node_to_delete->next;
        delete node_to_delete;
    }

    Arc* previous = NULL;
    for (int i = 0; i < NUMBER_OF_NODES; i++)
    {
        if (nodes[i])
        {
            node_to_delete = nodes[i];

            while (node_to_delete->value != node_value && node_to_delete->next)
            {
                previous = node_to_delete;
                node_to_delete = node_to_delete->next;
            }

            if (node_to_delete->value == node_value && previous != NULL)
            {
                previous->next = node_to_delete->next;
                delete node_to_delete;
            }
        }
    }

    return true;
}

bool delete_arc(Arc* nodes[], char start_node, char end_node)
{
    if (!search_arc(nodes, start_node, end_node)) return false;

    int beginning_node_index = search_node(nodes, start_node);
    Arc* beginning_node = nodes[beginning_node_index], *previous = NULL;

    while (beginning_node->value != end_node && beginning_node->next)
    {
        previous = beginning_node;
        beginning_node = beginning_node->next;
    }

    if (previous) {
        previous->next = beginning_node->next;
        delete beginning_node;
    }

    return true;
}

void check_input_arcs_count(Arc* nodes[])
{
    bool has_nodes = false;
    for (int i = 0; i < NUMBER_OF_NODES; i++)
    {
        if (nodes[i])
        {
            int counter = 0;
            Arc* current_node = nodes[i];
            for (int j = 0; j < NUMBER_OF_NODES; j++)
            {
                if (nodes[j])
                {
                    Arc* comparison_node = nodes[j];
                    while (comparison_node->next)
                    {
                        comparison_node = comparison_node->next;
                        if (comparison_node->value == current_node->value) {
                            counter++;
                            has_nodes = true;
                        }
                    }
                }
            }

            if (counter == 2) printf("\nNode %c has 2 input arcs.\n", current_node->value);
        }
    }
    if (!has_nodes) printf("\nThere aren't any nodes with 2 input arcs.\n");
}
