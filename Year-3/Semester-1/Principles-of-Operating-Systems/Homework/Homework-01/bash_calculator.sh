#!/bin/bash

# Function to validate if the input is a number
is_number() {
  if ! [[ $1 =~ ^-?[0-9]+([.][0-9]+)?$ ]]; then
    echo "Error: Input is not a valid number."
    return 1
  fi
  return 0
}

# Function to perform calculations
calculate() {
  while true; do
    read -p "Enter first number: " num1
    is_number $num1 || continue

    read -p "Enter second number: " num2
    is_number $num2 || continue

    read -p "Enter operation (+, -, *, /): " op

    case $op in
      +) let res=num1+num2; break;;
      -) let res=num1-num2; break;;
      \*) let res=num1*num2; break;;
      /)
        if [ $num2 -eq 0 ]; then
          echo "Division by zero is not allowed."
          continue
        else
          res=$(awk "BEGIN {print $num1/$num2; exit}")
          break
        fi
        ;;
      *) echo "Invalid operation. Please try again."; continue;;
    esac
  done

  echo "Result: $res"
}

# Function to ask user to continue or not
ask_continue() {
  while true; do
    read -p "Do you want to continue (Yes/No)? " answer
    case $answer in
      [Yy][Ee][Ss] ) return 0;;
      [Nn][Oo] ) return 1;;
      * ) echo "Invalid response. Please answer Yes or No.";;
    esac
  done
}

# Main loop
while true; do
  calculate
  ask_continue || break
done

echo "Program ended."
