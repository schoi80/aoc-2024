from aoc.utils import read_input


def part1(input_list: list[str]) -> int:
    """Part 1 of the problem."""
    for line in input_list:
        print(line)
    return 1

def part2(input_list: list[str]) -> int:
    """Part 2 of the problem."""
    for line in input_list:
        print(line)
    return 1

def main() -> None:
    """The main function."""
    val_input = read_input("Day01")
    result1 = part1(val_input)
    result2 = part2(val_input)
    print(result1)  
    print(result2)

if __name__ == "__main__":
    main()