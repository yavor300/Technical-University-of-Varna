﻿#include <iostream>
using namespace std;

void main()
{
	int number_of_pods, peas_per_pod, total_peas;

	cout << "Press return after entering a number.\n";
	cout << "Enter the number of pods:\n";
	cin >> number_of_pods;
	cout << "Enter the number of peas in a pod:\n";
	cin >> peas_per_pod;

	total_peas = number_of_pods * peas_per_pod;

	cout << "If you have " << number_of_pods << " pea pods\n" << "and "
		<< peas_per_pod << " peas in each pod, then\n"
		<< "you have " << total_peas << " peas in all the pods.\n";
}
