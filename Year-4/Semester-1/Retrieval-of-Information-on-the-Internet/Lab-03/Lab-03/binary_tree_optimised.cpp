#include <stddef.h>

#include <stdlib.h>

#include <stdio.h>

typedef int item_type;

typedef struct tree {
  item_type item;
  struct tree * parent;
  struct tree * left;
  struct tree * right;
}
tree;

void add_tree(tree ** t, item_type item, tree * parent) {
  if ( * t == NULL) {
    tree * p = (tree * ) malloc(sizeof(tree));
    p -> left = p -> right = NULL;
    p -> parent = parent;
    p -> item = item;
    * t = p;
    return;
  }

  if (item < ( * t) -> item) {
    add_tree( & (( * t) -> left), item, * t);
  } else {
    add_tree( & (( * t) -> right), item, * t);
  }
}

tree * search_tree(tree * t, item_type i) {
  if (t == NULL) return NULL;
  if (t -> item == i) return t;
  return (i < t -> item) ? search_tree(t -> left, i) : search_tree(t -> right, i);
}

tree * find_min(tree * t) {
  while (t != NULL && t -> left != NULL) {
    t = t -> left;
  }
  return t;
}

void del_element(tree * t, item_type item) {
  if (t == NULL) return;
  tree * element = search_tree(t, item);
  if (element == NULL) return;

  int hasParent = element -> parent != NULL;
  int isLeft = hasParent && element == element -> parent -> left;

  if (element -> left == NULL && element -> right == NULL) {
    if (hasParent) {
      if (isLeft) element -> parent -> left = NULL;
      else element -> parent -> right = NULL;
    }
    free(element);
  } else if (element -> left != NULL && element -> right == NULL) {
    element -> left -> parent = element -> parent;
    if (hasParent) {
      if (isLeft) element -> parent -> left = element -> left;
      else element -> parent -> right = element -> left;
    }
    free(element);
  } else if (element -> left == NULL && element -> right != NULL) {
    element -> right -> parent = element -> parent;
    if (hasParent) {
      if (isLeft) element -> parent -> left = element -> right;
      else element -> parent -> right = element -> right;
    }
    free(element);
  } else {
    tree * right_min = find_min(element -> right);
    element -> item = right_min -> item;
    del_element(right_min, right_min -> item);
  }
}

void print_inorder(tree * t) {
  if (t == NULL) return;
  print_inorder(t -> left);
  printf("%d ", t -> item);
  print_inorder(t -> right);
}

void print_preorder(tree * t) {
  if (t == NULL) return;
  printf("%d ", t -> item);
  print_preorder(t -> left);
  print_preorder(t -> right);
}

void print_postorder(tree * t) {
  if (t == NULL) return;
  print_postorder(t -> left);
  print_postorder(t -> right);
  printf("%d ", t -> item);
}

item_type find_max(tree * t) {
  if (t == NULL) {
    printf("The tree is empty.\n");
    return -1;
  }
  while (t -> right != NULL) t = t -> right;
  return t -> item;
}

void increment_tree(tree * t, int increment_value) {
  if (t == NULL) return;
  t -> item += increment_value;
  increment_tree(t -> left, increment_value);
  increment_tree(t -> right, increment_value);
}

int find_depth(tree * t) {
  if (t == NULL) return 0;
  int left_depth = find_depth(t -> left);
  int right_depth = find_depth(t -> right);
  return (left_depth > right_depth ? left_depth : right_depth) + 1;
}

void add_from_file(tree ** root,
  const char * filename) {
  FILE * file = fopen(filename, "r");
  if (file == NULL) {
    printf("Could not open file %s\n", filename);
    return;
  }
  int num;
  while (fscanf(file, "%d", & num) == 1) {
    add_tree(root, num, NULL);
  }
  fclose(file);
}

void free_tree(tree * t) {
  if (t == NULL) return;
  free_tree(t -> left);
  free_tree(t -> right);
  free(t);
}

int main_(void) {
  tree * root = NULL;
  add_from_file( & root, "numbers.txt");
  print_inorder(root);
  printf("\n");

  del_element(root, 16);
  increment_tree(root, 2);
  print_inorder(root);
  printf("\n");

  item_type max_val = find_max(root);
  printf("Max: %d\n", max_val);

  int depth = find_depth(root);
  printf("Depth: %d\n", depth);

  free_tree(root);
  getchar();
  return EXIT_SUCCESS;
}
