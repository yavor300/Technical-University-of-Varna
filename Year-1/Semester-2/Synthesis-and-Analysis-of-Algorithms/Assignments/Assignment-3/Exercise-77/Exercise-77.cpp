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

const int INITIALIZE_GRAPH_CHOICE = 1;
const int SEARCH_NODE_CHOICE = 2;
const int SEARCH_ARC_CHOICE = 3;
const int ADD_NODE_CHOICE = 4;
const int ADD_ARC_CHOICE = 5;
const int DELETE_NODE_CHOICE = 6;
const int DELETE_ARC_CHOICE = 7;
const int EXIT_FROM_PROGRAM_CHOICE = 8;

struct Arc {
    char value;
    Arc* next;
};
Arc* nodes[NUMBER_OF_NODES];

bool initialize(Arc* nodes[], bool& is_graph_initialized);
bool search_node(Arc* nodes[], char search_value);
bool search_arc(Arc* nodes[], char start_node, char end_node);
bool add_node(Arc* nodes[], char node_value);
bool add_arc(Arc* nodes[], char start_node, char end_node);
bool delete_node(Arc* nodes[], char node_value);
bool delete_arc(Arc* nodes[], char start_node, char end_node);

int main()
{
    bool is_graph_initialized = false;
    int menu_choice = 0;
    char start_node, end_node, node_value;

    do
    {
        printf("===== HOME MENU =====\n\nEnter %d, to initialize the graph.\nEnter %d to search for a node.\nEnter %d to search for an arc.\nEnter %d to add a node.\nEnter %d to add an arc.\nEnter %d to delete a node.\nEnter %d to delete an arc.\nEnter %d, to stop the program.\n",
            INITIALIZE_GRAPH_CHOICE, SEARCH_NODE_CHOICE, SEARCH_ARC_CHOICE, ADD_NODE_CHOICE, ADD_ARC_CHOICE, DELETE_NODE_CHOICE, DELETE_ARC_CHOICE, EXIT_FROM_PROGRAM_CHOICE);
        do
        {
            printf("Enter valid menu option [%d - %d]: ", INITIALIZE_GRAPH_CHOICE, EXIT_FROM_PROGRAM_CHOICE);
            cin >> menu_choice;
        } while (menu_choice < INITIALIZE_GRAPH_CHOICE || menu_choice > EXIT_FROM_PROGRAM_CHOICE);

        system("cls");
        switch (menu_choice)
        {
        case INITIALIZE_GRAPH_CHOICE:
            if (initialize(nodes, is_graph_initialized))
                printf("Graph is successfully initialized with space for %d nodes.\n", NUMBER_OF_NODES);
            else
                printf("Graph is already initialized!\n");
            break;

        case SEARCH_NODE_CHOICE:
            char search_value;
            printf("Enter search value: ");
            cin >> search_value;

            if (search_node(nodes, search_value))
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
        }
        cout << endl;
    } while (menu_choice != EXIT_FROM_PROGRAM_CHOICE);
}

bool initialize(Arc* nodes[], bool& is_graph_initialized)
{
    if (is_graph_initialized) return false;

    for (int i = 0; i < NUMBER_OF_NODES; i++) nodes[i] = NULL;

    is_graph_initialized = true;
    return true;
}

bool search_node(Arc* nodes[], char search_value)
{
    for (int i = 0; i < NUMBER_OF_NODES; i++)
    {
        if (nodes[i] && nodes[i]->value == search_value) return true;
    }
    return false;
}

bool search_arc(Arc* nodes[], char start_node, char end_node)
{
    if (!search_node(nodes, start_node) && !search_node(nodes, end_node)) return false;

    int index = 0;
    Arc* found_node;

    do
    {
        if (nodes[index] == NULL || (nodes[index] && nodes[index]->value != start_node)) index++;
    } while (nodes[index]->value != start_node);

    found_node = nodes[index];
    while (found_node->value != end_node && found_node->next) found_node = found_node->next;
    if (found_node->value == end_node) return true;

    return false;
}

bool add_node(Arc* nodes[], char node_value)
{
    if (search_node(nodes, node_value)) return false;

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

    int index = 0;
    Arc* next_node;

    if (!search_node(nodes, start_node)) add_node(nodes, start_node);
    if (!search_node(nodes, end_node)) add_node(nodes, end_node);

    while (nodes[index]->value != start_node) index++;

    next_node = new Arc;
    next_node->value = end_node;
    next_node->next = nodes[index]->next;
    nodes[index]->next = next_node;

    return true;
}

bool delete_node(Arc* nodes[], char node_value)
{
    if (!search_node(nodes, node_value)) return false;

    int index = 0;
    do
    {
        if (!nodes[index] || (nodes[index] && nodes[index]->value != node_value)) index++;
    } while (nodes[index]->value != node_value);

    Arc* found_node = nodes[index], *q = NULL;
    while (nodes[index])
    {
        found_node = nodes[index];
        nodes[index] = found_node->next;
        delete found_node;
    }

    for (index = 0; index < NUMBER_OF_NODES; index++)
    {
        if (nodes[index])
        {
            found_node = nodes[index];
            while (found_node->value != node_value && found_node->next)
            {
                q = found_node;
                found_node = found_node->next;
            }
            if (found_node->value == node_value)
            {
                q->next = found_node->next;
                delete found_node;
            }
        }
    }
    return true;
}

bool delete_arc(Arc* nodes[], char start_node, char end_node)
{
    if (!search_arc(nodes, start_node, end_node)) return false;

    int index = 0;
    while (nodes[index]->value != start_node) index++;

    Arc* found_node = nodes[index], *temp = NULL;
    while (found_node->value != end_node)
    {
        temp = found_node;
        found_node = found_node->next;
    }
    temp->next = found_node->next;
    delete found_node;
    return true;
}
