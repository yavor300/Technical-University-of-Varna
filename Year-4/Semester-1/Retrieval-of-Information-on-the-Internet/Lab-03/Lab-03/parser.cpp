#include <iostream>

#include <string>

#include <cctype>

#include <fstream>

using namespace std;

enum TSymbolType {
  intconst,
  text,
  semicolon,
  period,
  quotas,
  othersy
};

TSymbolType Symbol; // Global symbol tracker
string Spelling; // Holds the current string token
int Constant; // Holds the current integer token
string input; // Input text to parse
size_t currentCharIndex = 0; // Tracks the position in the input

// Function to get the next character from the input
char GetNextChar() {
  if (currentCharIndex < input.size()) {
    return input[currentCharIndex++];
  }
  return '\0';
}

// Function to skip any non-valid characters and get the next valid symbol
void GetNextSymbol() {
  Spelling.clear();
  Constant = 0;
  char Char = GetNextChar();

  while (Char == ' ' || Char < 32 || Char > 126) {
    Char = GetNextChar();
  }

  if (isalpha(Char)) {
    while (isalpha(Char) || isdigit(Char)) {
      Spelling += toupper(Char);
      Char = GetNextChar();
    }
    currentCharIndex--;
    Symbol = text;
    cout << "Parsed string: " << Spelling << endl;
  } else if (isdigit(Char)) {
    while (isdigit(Char)) {
      Constant = Constant * 10 + (Char - '0');
      Char = GetNextChar();
    }
    currentCharIndex--;
    Symbol = intconst;
    cout << "Parsed integer constant: " << Constant << endl;
  } else {
    switch (Char) {
    case '.':
      Symbol = period;
      cout << "Parsed period\n";
      break;
    case ';':
      Symbol = semicolon;
      cout << "Parsed semicolon\n";
      break;
    case '"':
      Symbol = quotas;
      cout << "Parsed quotas\n";
      break;
    default:
      Symbol = othersy;
      cout << "Error: unexpected symbol '" << Char << "' (ASCII code: " << int(Char) << ")" << endl;
      break;
    }
  }
}

// Field function: Handle an integer, a quoted string, or special characters
void Field() {
  if (Symbol == intconst) {
    GetNextSymbol();
  } else if (Symbol == text) {
    GetNextSymbol();
  } else if (Symbol == quotas) {
    GetNextSymbol();
    if (Symbol == text) {
      GetNextSymbol();
    }
    if (Symbol == quotas) {
      GetNextSymbol();
    } else {
      cout << "Error: expected closing quotes.\n";
    }
  } else {
    cout << "Error: field expects intconst, string, or quotes." << endl;
  }
}

// Record function: Handle a sequence of fields, ending with a period
void Record() {
  Field();
  while (Symbol == semicolon) {
    GetNextSymbol();
    Field();
  }

  if (Symbol == period) {
    if (currentCharIndex < input.size()) {
      GetNextSymbol();
    }
  } else {
    cout << "Error: expected period at the end of the record.\n";
  }
}

// DataFile function: Handle the entire data file structure
void DataFile() {
  while (currentCharIndex < input.size()) {
    Record();

    if (currentCharIndex >= input.size()) {
      break;
    }
  }
  printf("Finish\n");
}

bool ReadInputFromFile(const string & filename) {
  ifstream file(filename);
  if (!file.is_open()) {
    cout << "Error: Could not open the file " << filename << endl;
    return false;
  }

  string line;
  input.clear();
  while (getline(file, line)) {
    input += line;
  }

  file.close();
  return true;
}

// Main function demonstrating parsing
int __main() {

  string filename = "input.txt";
  if (!ReadInputFromFile(filename)) {
    return 1;
  }

  GetNextSymbol();

  DataFile();

  getchar();

  return 0;
}