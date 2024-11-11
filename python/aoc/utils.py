import hashlib

def read_input(name: str) -> list[str]:
    """Reads lines from the given input txt file."""
    return [line.strip() for line in open(f"data/{name}.txt")]

def string_to_md5(s: str) -> str:
    """Converts a string to MD5 hash."""
    return hashlib.md5(s.encode()).hexdigest().zfill(32)

def cap_number(num: int, cap: int) -> int:
    """Caps a number. Wrap around if exceeds."""
    return num % cap

def hex_to_binary(hex_char: str) -> str:
    """Converts a hexadecimal character to binary."""
    return bin(int(hex_char, 16))[2:].zfill(4)

def gcd(a: int, b: int) -> int:
    """Calculates the greatest common divisor."""
    while b != 0:
        a, b = b, a % b
    return abs(a)

def lcm(a: int, b: int) -> int:
    """Calculates the least common multiple."""
    return abs(a*b) // gcd(a, b)

def intersects(p1: tuple[float, float], p2: tuple[float, float]) -> bool:
    """Checks if two pairs of floats intersect."""
    return p1[0] <= p2[1] and p2[0] <= p1[1]
