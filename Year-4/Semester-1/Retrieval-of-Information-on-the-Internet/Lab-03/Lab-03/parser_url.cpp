#include <iostream>

#include <string>

#include <cctype>

#include <fstream>

using namespace std;

enum TSymbolType {
  http_prefix,
  slashes,
  hostname,
  hostnumber,
  hostport,
  path,
  period,
  colon,
  othersy
};

TSymbolType Symbol; // Global symbol tracker
string Spelling; // Holds the current string token
string input; // Input text to parse
size_t currentCharIndex = 0; // Tracks the position in the input

// Function to get the next character from the input
char GetNextChar() {
  if (currentCharIndex < input.size()) {
    return input[currentCharIndex++];
  }
  return '\0';
}

// Function to parse the full path starting with '/'
void ParsePath() {
  string path;
  char Char = GetNextChar();

  while (Char != '\0' && Char != ' ') {
    path += Char;
    Char = GetNextChar();
  }

  if (!path.empty()) {
    cout << "Parsed path: " << path << endl;
  } else {
    cout << "Error: Expected path after slash\n";
  }
}

// Function to skip any non-valid characters and get the next valid symbol
void GetNextSymbol() {
  Spelling.clear();
  char Char = GetNextChar();

  while (Char == ' ') {
    Char = GetNextChar();
  }

  if (input.substr(currentCharIndex - 1, 7) == "http://") {
    Symbol = http_prefix;
    currentCharIndex += 6;
    cout << "Parsed: http:// (HTTP prefix)\n";
  } else if (Char == '/') {
    Symbol = slashes;
  } else if (isdigit(Char)) {
    Spelling += Char;
    Char = GetNextChar();
    while (isdigit(Char) || Char == '.') {
      Spelling += Char;
      Char = GetNextChar();
    }
    currentCharIndex--;
    if (Spelling.find('.') != string::npos) {
      Symbol = hostnumber;
      cout << "Parsed hostnumber: " << Spelling << endl;
    } else {
      Symbol = hostport;
      cout << "Parsed port: " << Spelling << endl;
    }
  } else if (isalpha(Char)) {
    while (isalpha(Char) || Char == '.' || isdigit(Char)) {
      Spelling += Char;
      Char = GetNextChar();
    }
    currentCharIndex--;
    Symbol = hostname;
    cout << "Parsed hostname: " << Spelling << endl;
  } else if (Char == ':') {
    Spelling += Char;
    Symbol = colon;
    cout << "Parsed: : (Colon)\n";
  } else {
    Symbol = othersy;
    cout << "Error: unexpected symbol '" << Char << "'\n";
  }
}

// Function to parse a hostname or hostnumber
void ParseHost() {
  GetNextSymbol();
  if (Symbol == hostname || Symbol == hostnumber) {
    GetNextSymbol();
    if (Symbol == colon) {

      GetNextSymbol();
      if (Symbol != hostport) {
        cout << "Error: Expected port number after colon\n";
        return;
      }
      GetNextSymbol();
    }

    if (Symbol == slashes) {
      ParsePath();
    }
  } else {
    cout << "Error: Expected hostname or hostnumber\n";
  }
}

// URL parsing function
void ParseURL() {
  GetNextSymbol();
  if (Symbol == http_prefix) {
    ParseHost();
  } else {
    cout << "Error: Expected HTTP prefix\n";
  }
}

// Function to read input from a file
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

int _main() {

  string filename = "url_input.txt";
  if (!ReadInputFromFile(filename)) {
    return 1;
  }

  ParseURL();

  getchar();

  return 0;
}
