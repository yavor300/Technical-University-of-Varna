#include <iostream>
#include <string>
#include <cassert>
using namespace std;

typedef int fsm_state;
typedef char fsm_input;

bool is_final_state(fsm_state state) {
    return (state == 2);
}

fsm_state get_start_state() {
    return 0;
}

fsm_state move(fsm_state state, fsm_input input) {
    switch (state) {
        case 0:
            // Allow optional '+' or '-' at the start
            if (input == '+' || input == '-') return 1;
            if (isdigit(input)) return 2;
            break;
        case 1:
            if (isdigit(input)) return 2;
            break;
        case 2:
            if (isdigit(input)) return 2;
            break;
        default:
            assert(0);
    }
    return -1;
}

bool recognize(const string& str) {
    if (str.empty()) return false;

    fsm_state state = get_start_state();
    for (char input : str) {
        state = move(state, input);
        if (state == -1) return false;
    }
    return is_final_state(state);
}

int main() {
    string test_str = "-12345";
    if (recognize(test_str)) {
        cout << "String accepted: " << test_str << endl;
    } else {
        cout << "String not accepted: " << test_str << endl;
    }
		getchar();
    return 0;
}
