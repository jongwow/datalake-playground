package main

import (
	"bufio"
	"flag"
	"fmt"
	"os"
	"os/exec"
	"strings"
)

func main() {
	kubeNamespace := "ns-datalake"
	runCommand(kubeNamespace)
}
func runCommand(kubeNamespace string) {
	manifestFile := flag.String("f", "", "Path to the Kubernetes manifest file")
	flag.Parse()

	if *manifestFile == "" {
		fmt.Println("Please specify the manifest file path using -f flag")
		return
	}

	fmt.Println("Manifest file is " + *manifestFile)

	cmd := exec.Command("kubectl", "diff", "-f", *manifestFile, "-n", kubeNamespace)
	diffOutput, err := cmd.CombinedOutput()
	if err != nil && !strings.Contains(err.Error(), "exit status 1") {
		fmt.Printf("Error running kubectl diff: %s\n", err)
		fmt.Printf(": %s\n", err.Error())
		return
	}

	// Check if there's any output from diff
	if len(diffOutput) == 0 {
		fmt.Println("No changes detected")
		return
	}

	fmt.Println("Changes detected: ")
	fmt.Println(string(diffOutput))

	reader := bufio.NewReader(os.Stdin)
	fmt.Printf("Do you want to apply these changes? (y/n): ")
	response, err := reader.ReadString('\n')
	if err != nil {
		fmt.Printf("Error reading input: %s\n", err)
		return
	}
	if response[0] == 'y' || response[0] == 'Y' {
		applyCmd := exec.Command("kubectl", "apply", "-f", *manifestFile, "-n", kubeNamespace)
		applyOutput, err := applyCmd.CombinedOutput()
		if err != nil {
			fmt.Printf("Error applying changes: %s\n", err)
			return
		}
		fmt.Println("Changes applied: ")
		fmt.Println(string(applyOutput))
	} else {
		fmt.Println("Apply aborted. ")
	}

}
