import os

try:
    os.system("clear")
    print("oxyum services since 2024. Type 'help' for help")
except:
    os.system("cls")
    print("oxyum services since 2024. Type 'help' for help")

while(True):
    user_input = input(">>> ")
    if user_input == "exit":
        break
    elif user_input.startswith("execute "):
        input_file = user_input.split(" ")[1]
        os.system(f"javac backend/*.java modules/*.java Main.java")
        os.system(f"java Main {input_file}")
    elif user_input == "clear":
        os.system("clear")
    elif user_input == "help":
        print("exit - exit the terminal")
        print("execute <filename> - execute the program with the specified file")
        print("clear - clear the terminal")
    elif user_input == "ls":
        os.system("ls")
    elif user_input == "execute -c":
        command_input = input("!EXECUTE >>> ")
        os.system(command_input)
    elif user_input == "^[[A":
        os.system(f"javac backend/*.java modules/*.java Main.java")
        os.system(f"java Main example.nuxj")
    else:
        print("Invalid command")