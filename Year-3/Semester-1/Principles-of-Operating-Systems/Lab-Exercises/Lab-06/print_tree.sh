print_tree() {

  directory=$1
  symbol=$2

  echo ${symbol}${directory}

 deep_symbol="$symbol "
  for item in $directory/*;
  do
          if [ -d $item ];
          then

                  print_tree $item $deep_symbol
          else
                  echo $deep_symbol$item
          fi
  done

}

print_tree $1 "-"

