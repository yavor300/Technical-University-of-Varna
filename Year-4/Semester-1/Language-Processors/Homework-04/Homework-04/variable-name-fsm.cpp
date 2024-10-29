#include <iostream>
#include <string>
#include <cassert>
using namespace std;

typedef int fsm_state;
typedef char fsm_input;

bool is_final_state(fsm_state state) {
    return (state == 1);
}

fsm_state get_start_state() {
    return 0;
}

fsm_state move(fsm_state state, fsm_input input) {
    switch (state) {
        case 0:
            if (input == 'a' || input == 'b') return 1;
            break;
        case 1:
            if (input == 'a' || input == 'b' || input == '0' || input == '1') return 1;
            break;
        default:
            assert(0);
            return -1;
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
    string test_str = "ab01";
    if (recognize(test_str)) {
        cout << "String accepted: " << test_str << endl;
    } else {
        cout << "String not accepted: " << test_str << endl;
    }
    return 0;
}
