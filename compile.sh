# #!/bin/sh
# [[ $_ != $0 ]] && : || echo -e "\033[0;33mWARNING : Script is a subshell, Auto Completion wont work. Re-run with :\n\t \033[0;32m$ source compile.sh \033[0m"

# _my() {
# #    local args cur opts
#     COMPREPLY=()
#     cur="${COMP_WORDS[COMP_CWORD]}"
#     prev="${COMP_WORDS[COMP_CWORD-1]}"
#     opts="client serveur"
#     COMPREPLY=( $(compgen -W "${opts}" -- ${cur}) )
# }
# complete -F _my ./run.sh
# complete -F _my run.sh
rm -rf classes/*
mkdir classes
javac -cp classes/ -d classes src/projetrmi/*.java
# sh ./UML/compile.sh
