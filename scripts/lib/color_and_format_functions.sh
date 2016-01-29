#!/bin/sh

display_color_matrix() {
  for clbg in {40..47} {100..107} 49 ; do
  	#Foreground
  	for clfg in {30..37} {90..97} 39 ; do
  		#Formatting
  		for attr in 0 1 2 4 5 7 ; do
  			#Print the result
  			echo -en "\e[${attr};${clbg};${clfg}m ^[${attr};${clbg};${clfg}m"
  		done
  		echo #Newline
  	done
  done
}

red_text='\e[1;31m'
bright_green_text='\e[49;92m'
black_text_bright_green_background='\e[7;40;92m'
black_text_white_background='\e[7;49;97m'
white_text_black_background='\e[7;107;30m'
white_text_bright_red_background='\e[7;107;91m'
black_text_bright_yellow_background='\e[7;49;93m'
blue_text_cyan_background='\e[7;44;96m'

display_success() {
  echo -e "$black_text_bright_green_background$1\e[0m"
}

display_error() {
  echo -e "$white_text_bright_red_background$1\e[0m"
}

display_warning() {
  echo -e "$black_text_bright_yellow_background$1\e[0m"
}

display_info() {
  echo -e "$blue_text_cyan_background$1\e[0m"
}

run_display_test() {
  display_success 'success'
  display_failure 'failure'
  display_info 'info'
  display_warning 'warning'
}
