#!/bin/bash

# =========[ Color & Style Setup ]=========
GREEN="\033[1;32m"
RED="\033[1;31m"
YELLOW="\033[1;33m"
CYAN="\033[1;36m"
BLUE="\033[1;34m"
MAGENTA="\033[1;35m"
RESET="\033[0m"
BOLD="\033[1m"
WHITE="\033[1;37m"

# =========[ ASCII Banner ]=========
echo -e "${CYAN}"
cat << "EOF"
   _____       _            _       _             
  / ____|     | |          | |     | |            
 | |     __ _ | |  ___  _  | | __ _| |_ ___  _ __ 
 | |    / _` || | / __|(_) | |/ _` | __/ _ \| '__|
 | |___| (_| || || (__  _  | | (_| | || (_) | |   
  \_____\__,_||_| \___|(_) |_|\__,_|\__\___/|_|   
                                                 
EOF
echo -e "${RESET}"

echo -e "${BLUE}${BOLD}═════════════════════════════════════════════════${RESET}"
echo -e "${YELLOW}${BOLD}        Advanced Calculator Installation        ${RESET}"
echo -e "${BLUE}${BOLD}═════════════════════════════════════════════════${RESET}"

# =========[ Paths & Variables ]=========
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"
SRC="$PROJECT_ROOT/src/AdvancedCalculator.java"
WRAPPER="$PROJECT_ROOT/bin/calculate"
INSTALL_DIR="$HOME/.local/share/advanced-calculator"
SYMLINK_DIR="$HOME/.local/bin"
SYMLINK="$SYMLINK_DIR/calculate"

mkdir -p "$INSTALL_DIR" "$SYMLINK_DIR"

# =========[ Function: Progress Bar ]=========
progress_bar() {
    local duration=$1
    local steps=20
    local delay=$(echo "$duration / $steps" | bc -l)
    echo -ne "${YELLOW}["
    for ((i = 0; i < steps; i++)); do
        echo -ne "${GREEN}#"
        sleep "$delay"
    done
    echo -e "${YELLOW}] ${GREEN}Done!${RESET}"
}

# =========[ Function: Java Check & Install ]=========
check_and_install_java() {
    echo -e "${CYAN}${BOLD}Checking Java...${RESET}"

    if ! command -v java &> /dev/null || ! command -v javac &> /dev/null; then
        echo -e "${YELLOW}Java or javac not found.${RESET}"
        read -p "$(echo -e ${MAGENTA}Would you like to install Java now? ${WHITE}[y/n]${RESET}): " user_choice
        if [[ "$user_choice" =~ ^[Yy]$ ]]; then
            . /etc/os-release 2>/dev/null
            OS=$NAME

            echo -e "${CYAN}Installing Java on ${WHITE}$OS${RESET}"
            case "$OS" in
                *Ubuntu*|*Debian*)
                    sudo apt update && sudo apt install -y default-jdk
                    ;;
                *Fedora*|*CentOS*|*RHEL*)
                    sudo dnf install -y java-latest-openjdk-devel
                    ;;
                *Arch*)
                    sudo pacman -S jdk-openjdk
                    ;;
                *openSUSE*)
                    sudo zypper install -y java-17-openjdk-devel
                    ;;
                *Alpine*)
                    sudo apk add openjdk17-jdk
                    ;;
                *Darwin*|*Mac*)
                    if ! command -v brew &> /dev/null; then
                        echo -e "${YELLOW}Installing Homebrew...${RESET}"
                        /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
                    fi
                    brew install openjdk
                    ;;
                *)
                    echo -e "${RED}Unsupported OS. Please install Java manually.${RESET}"
                    exit 1
                    ;;
            esac
            progress_bar 2
        else
            echo -e "${RED}Java is required. Exiting.${RESET}"
            exit 1
        fi
    else
        echo -e "${GREEN}${BOLD}✓ Java is installed${RESET}"
    fi
}

# =========[ Function: Compile Calculator ]=========
compile_calculator() {
    echo -e "${CYAN}Compiling Java source...${RESET}"
    cp "$SRC" "$INSTALL_DIR/"
    cp "$WRAPPER" "$INSTALL_DIR/"
    javac "$INSTALL_DIR/AdvancedCalculator.java"
    chmod +x "$INSTALL_DIR/calculate"
    ln -sf "$INSTALL_DIR/calculate" "$SYMLINK"
    progress_bar 1
}

# =========[ Function: Configure Shell ]=========
configure_shell_env() {
    echo -e "${CYAN}${BOLD}Configuring shell PATH...${RESET}"

    CURRENT_SHELL=$(basename "$SHELL")
    echo -e "${CYAN}Detected shell: ${WHITE}$CURRENT_SHELL${RESET}"

    case "$CURRENT_SHELL" in
        bash)  RC="$HOME/.bashrc" ;;
        zsh)   RC="$HOME/.zshrc" ;;
        fish)  RC="$HOME/.config/fish/config.fish" ;;
        *)
            read -p "$(echo -e ${MAGENTA}Unknown shell. Use bash/zsh/fish? ${WHITE}[bash/zsh/fish]${RESET}): " CUSTOM_SHELL
            case "$CUSTOM_SHELL" in
                bash) RC="$HOME/.bashrc" ;;
                zsh) RC="$HOME/.zshrc" ;;
                fish) RC="$HOME/.config/fish/config.fish" ;;
                *) echo -e "${RED}Unsupported shell. Exiting.${RESET}"; exit 1 ;;
            esac
            ;;
    esac

    if [[ "$CURRENT_SHELL" == "fish" ]]; then
        mkdir -p "$(dirname "$RC")"
        grep -q "set -x PATH $SYMLINK_DIR" "$RC" 2>/dev/null || echo "set -x PATH $SYMLINK_DIR \$PATH" >> "$RC"
    else
        grep -q 'export PATH="$HOME/.local/bin:$PATH"' "$RC" 2>/dev/null || echo 'export PATH="$HOME/.local/bin:$PATH"' >> "$RC"
    fi

    echo -e "${GREEN}✓ Environment updated in: ${WHITE}$RC${RESET}"
    echo -e "${YELLOW}Restart your terminal or run 'source $RC' to apply changes.${RESET}"
}

# =========[ Main Flow ]=========
check_and_install_java
compile_calculator
configure_shell_env

# =========[ Done ]=========
echo -e "${BLUE}${BOLD}═════════════════════════════════════════════════${RESET}"
echo -e "${GREEN}${BOLD}        Installation Complete! 🚀        ${RESET}"
echo -e "${BLUE}${BOLD}═════════════════════════════════════════════════${RESET}"
echo -e "${YELLOW}${BOLD}You can now run the calculator by typing: ${WHITE}calculate${RESET}"
echo ""

