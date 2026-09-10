# Java & OOP Interview Preparation — Day 1

## 1. Java Data Types

Java is a **statically typed** language, meaning every variable must have a declared type at compile time.

Java data types are divided into:

### Primitive Data Types

Java has 8 primitive types:

| Type      |          Size | Default value | Example                |
| --------- | ------------: | ------------- | ---------------------- |
| `byte`    |         8-bit | `0`           | `byte b = 10;`         |
| `short`   |        16-bit | `0`           | `short s = 100;`       |
| `int`     |        32-bit | `0`           | `int x = 1000;`        |
| `long`    |        64-bit | `0L`          | `long x = 100L;`       |
| `float`   |        32-bit | `0.0f`        | `float x = 10.5f;`     |
| `double`  |        64-bit | `0.0d`        | `double x = 10.5;`     |
| `char`    |        16-bit | `'\u0000'`    | `char c = 'A';`        |
| `boolean` | JVM-dependent | `false`       | `boolean flag = true;` |

> Important: Java does not specify a fixed memory representation for `boolean`; its size is JVM implementation-dependent.

### Reference Types

Reference types include:

* Classes
* Interfaces
* Arrays
* Enums
* Records
* Other object types

Example:

```java
String name = "Adrija";
int[] numbers = {1, 2, 3};
```

`name` and `numbers` are references to objects, rather than primitive values themselves.

---

### Interview Question: Where are primitive variables stored?

**Answer:**

It depends on **where the variable is declared**, not on the fact that it's a primitive.

**1. Local variables → Stack**
```java
void test() {
    int x = 10; // stored in the stack frame of test()
}
```
`x` exists only within the method's stack frame and is destroyed once the method returns.

**2. Instance fields → Heap**
```java
class Employee {
    int age;
}
```
When an `Employee` object is created, `age` becomes part of that object, so it lives on the **heap** along with the rest of the object.

**3. Static fields → Method Area (Metaspace in modern JVMs)**
```java
class Employee {
    static int count;
}
```
Static variables belong to the class, not any instance, so they live in the **method area** (Metaspace, post Java 8), separate from both stack and heap.

**Key takeaway:** Don't say "primitives are stored on the stack." The correct answer is: *local primitives live on the stack, primitives that are part of an object live on the heap, and primitives declared static live in the method area.*

---

# 2. Wrapper Classes

Java provides wrapper classes that represent primitive values as objects.

| Primitive | Wrapper     |
| --------- | ----------- |
| `byte`    | `Byte`      |
| `short`   | `Short`     |
| `int`     | `Integer`   |
| `long`    | `Long`      |
| `float`   | `Float`     |
| `double`  | `Double`    |
| `char`    | `Character` |
| `boolean` | `Boolean`   |

### Why do we need wrapper classes?

**1. Collections work with objects, not primitives**

Java's collection framework requires objects:

```java
List<Integer> numbers = new ArrayList<>();
```

You cannot use a primitive type directly:

```java
List<int> numbers; // Invalid
```

**2. Utility methods**

Wrapper classes provide useful static methods that primitives don't have:

```java
Integer.parseInt("100");
Integer.compare(10, 20);
Integer.MAX_VALUE;
```

**3. Autoboxing and unboxing**

Since Java 5, the compiler automatically converts between primitives and their wrappers:

```java
Integer i = 10;       // autoboxing: int -> Integer
int j = i;             // unboxing: Integer -> int
```

**4. Nullability**

Primitives can never be `null`, but wrapper types can — useful when a value may be "absent" (e.g., optional fields, database results):

```java
Integer score = null; // valid
int score = null;      // compile error
```

---

# 3. Autoboxing and Unboxing

### Autoboxing

Automatic conversion from primitive → wrapper.

```java
int x = 10;

Integer obj = x;
```

Conceptually:

```java
Integer obj = Integer.valueOf(x);
```

### Unboxing

Automatic conversion from wrapper → primitive.

```java
Integer obj = 10;

int x = obj;
```

Conceptually:

```java
int x = obj.intValue();
```

---

## Interview Question: What is the difference between `Integer.valueOf()` and `new Integer()`?

Modern Java code should use:

```java
Integer x = Integer.valueOf(10);
```

rather than explicitly creating wrapper objects.

`valueOf()` can reuse cached wrapper objects where applicable.

Also, constructors such as `new Integer(int)` have been deprecated.

---

## Important Interview Trap: Integer Caching

```java
Integer a = 100;
Integer b = 100;

System.out.println(a == b);
```

Output:

```text
true
```

But:

```java
Integer a = 200;
Integer b = 200;

System.out.println(a == b);
```

Typically:

```text
false
```

Why?

Autoboxing uses `Integer.valueOf()`, and Java caches commonly used `Integer` instances, including the range `-128` to `127` as required by the Java specification.

Therefore:

```java
== 
```

compares the references, not the integer values.

For value comparison:

```java
a.equals(b)
```

---

# 4. String Pool

Strings are objects in Java.

```java
String a = "hello";
String b = "hello";
```

String literals are stored in the **String Pool**, which is part of the JVM's heap.

The JVM can reuse the same pooled String object:

```text
a ─────┐
       ├──> "hello"
b ─────┘
```

Therefore:

```java
System.out.println(a == b);
```

prints:

```text
true
```

But:

```java
String a = new String("hello");
String b = new String("hello");
```

creates distinct objects:

```text
a ──> "hello" object
b ──> "hello" object
```

So:

```java
a == b
```

is `false`.

But:

```java
a.equals(b)
```

is `true`.

---

# 5. Why is String Immutable?

A `String` object cannot be modified after it is created.

```java
String s = "hello";

s.concat(" world");

System.out.println(s);
```

Output:

```text
hello
```

`concat()` creates a new String rather than modifying the existing one.

```java
s = s.concat(" world");
```

Now `s` refers to the new String.

### Why is immutability important?

#### 1. String Pool

Because Strings cannot change, multiple references can safely share the same String.

#### 2. Security

Strings are commonly used for:

* file paths
* URLs
* class names
* database connection information
* security-related values

If Strings were mutable, their values could potentially change after validation.

#### 3. Thread Safety

Immutable objects can safely be shared between threads without synchronization for state changes.

#### 4. Hashing

Strings are frequently used as keys in `HashMap`.

Their hash value can safely be cached because their contents never change.

---

## Interview Question: Is String immutable because of `final`?

**No.**

There are two different concepts.

A `final` reference means:

```java
final String s = "hello";
```

You cannot make `s` refer to another String.

But String immutability means the **String object's state itself cannot be changed**.

The `String` class is also `final`, preventing subclasses from changing its behavior, but that alone is not what makes its contents immutable.

---

# 6. StringBuilder vs StringBuffer

Both classes are used for mutable sequences of characters.

### StringBuilder

```java
StringBuilder sb = new StringBuilder();

sb.append("Hello");
sb.append(" World");
```

Advantages:

* Mutable
* Faster in typical single-threaded scenarios
* Methods are not synchronized

### StringBuffer

```java
StringBuffer sb = new StringBuffer();

sb.append("Hello");
sb.append(" World");
```

Advantages:

* Mutable
* Methods are synchronized
* Designed for thread-safe use

### Interview comparison

| Feature             | String                          | StringBuilder                     | StringBuffer                  |
| ------------------- | ------------------------------- | --------------------------------- | ----------------------------- |
| Mutable             | ❌                               | ✅                                 | ✅                             |
| Thread-safe         | Yes, due to immutability        | No                                | Yes                           |
| Synchronization     | N/A                             | No                                | Yes                           |
| Typical performance | Poor for repeated modifications | Best for single-threaded mutation | Generally slower than Builder |

### Interview Question

**Why is StringBuilder generally faster than StringBuffer?**

Because `StringBuffer` synchronizes its methods, introducing synchronization overhead.

For normal single-threaded code, `StringBuilder` is generally preferred.

---

# 7. `==` vs `equals()`

This is one of the most common Java interview questions.

### `==`

For primitives:

```java
int a = 10;
int b = 10;

a == b
```

compares values.

For references:

```java
String a = new String("hello");
String b = new String("hello");

a == b
```

compares object references.

### `equals()`

`equals()` is intended for **logical/content equality**.

```java
a.equals(b)
```

For String:

```java
"hello".equals(new String("hello"))
```

returns:

```text
true
```

because String overrides `equals()` to compare character sequences.

---

## Interview Question

### What is the output?

```java
String a = "Java";
String b = "Java";
String c = new String("Java");

System.out.println(a == b);
System.out.println(a == c);
System.out.println(a.equals(c));
```

Answer:

```text
true
false
true
```

Reason:

* `a` and `b` refer to the same pooled String.
* `c` is a separate String object.
* `equals()` compares String contents.

---

# 8. `equals()` and `hashCode()` Contract

This is **very important for product-company interviews.**

The contract says:

> If two objects are equal according to `equals()`, they must have the same `hashCode()`.

Therefore:

```java
a.equals(b) == true
```

must imply:

```java
a.hashCode() == b.hashCode()
```

However, the reverse is **not required**.

Two unequal objects can have the same hash code. This is called a **hash collision**.

---

## Why does this matter?

Consider:

```java
HashMap<Employee, String> map = new HashMap<>();
```

HashMap uses:

1. `hashCode()` to determine the bucket.
2. `equals()` to distinguish keys within that bucket.

If you override `equals()` but not `hashCode()`, logically equal objects may end up in different buckets.

That can break expected HashMap/HashSet behavior.

---

## Interview Question

### What happens if you override `equals()` but not `hashCode()`?

**Answer:**

The class violates the general `equals()`/`hashCode()` contract.

Two logically equal objects may have different hash codes, causing hash-based collections such as `HashMap` and `HashSet` to behave incorrectly.

---

# 9. `static` vs Instance Members

### Instance member

Belongs to an object.

```java
class Employee {
    String name;
}
```

Each object has its own `name`.

```java
Employee e1 = new Employee();
Employee e2 = new Employee();

e1.name = "A";
e2.name = "B";
```

### Static member

Belongs to the class rather than individual objects.

```java
class Employee {
    static String company = "Google";
}
```

All instances share the same static variable.

```java
Employee.company
```

is preferred over:

```java
e1.company
```

---

## Interview Question: Can a static method access an instance variable directly?

No.

```java
class Test {

    int x = 10;

    static void print() {
        System.out.println(x); // Compile error
    }
}
```

Why?

A static method belongs to the class and can execute without an object existing.

There may therefore be no particular `x` to access.

You need an object:

```java
static void print() {
    Test obj = new Test();
    System.out.println(obj.x);
}
```

---

## Can an instance method access static members?

Yes.

```java
class Test {

    static int count = 10;

    void print() {
        System.out.println(count);
    }
}
```

An instance method has access to the class-level static state.

---

# 10. `final`, `finally`, `finalize`

These are completely different concepts.

## `final`

Used with variables, methods and classes.

### Final variable

```java
final int x = 10;
```

`x` cannot be reassigned.

### Final method

```java
final void display() {}
```

A subclass cannot override it.

### Final class

```java
final class Vehicle {}
```

The class cannot be extended.

---

## `finally`

Used with exception handling.

```java
try {
    // risky operation
} catch (Exception e) {
    // handle exception
} finally {
    // cleanup
}
```

The `finally` block is generally executed whether an exception occurs or not, although there are exceptional circumstances such as JVM termination.

---

## `finalize()`

Historically, `finalize()` was a method associated with garbage collection and was intended to provide an opportunity for cleanup before an object was reclaimed.

However:

**`finalize()` has been deprecated since Java 9 and is not a mechanism that modern Java applications should rely upon.**

Modern Java code should use explicit resource management such as:

```java
try-with-resources
```

---

# 11. Access Modifiers

Java provides four access levels.

| Modifier    | Same Class | Same Package | Subclass | Other Package |
| ----------- | ---------- | ------------ | -------- | ------------- |
| `private`   | ✅          | ❌            | ❌*       | ❌             |
| default     | ✅          | ✅            | ✅**      | ❌             |
| `protected` | ✅          | ✅            | ✅        | Limited       |
| `public`    | ✅          | ✅            | ✅        | ✅             |

`*` A subclass cannot directly access a private member.

`**` Package access can make a member available to a subclass when the subclass is in the same package.

### `private`

Accessible only within the declaring class.

### default/package-private

No modifier:

```java
int age;
```

Accessible within the same package.

### `protected`

Accessible:

* within the same package
* in subclasses outside the package, subject to Java's protected-access rules

### `public`

Accessible wherever the class/member is accessible.

---

# 12. JVM Architecture

This is where your **"what happens in memory?"** questions start.

At a high level:

```text
                 JVM
                  |
      -------------------------
      |           |           |
  Class Loader   Runtime     Execution
                 Data Area    Engine
```

---

# 13. Class Loading

When Java runs:

```java
java Main
```

the JVM doesn't immediately execute the source code.

The source code:

```text
Main.java
```

is compiled into:

```text
Main.class
```

The JVM's **Class Loader subsystem** loads the class.

Conceptually, class loading involves:

1. **Loading**
2. **Linking**

   * Verification
   * Preparation
   * Resolution
3. **Initialization**

---

# 14. Heap

The **heap** is the runtime memory area where objects and arrays are allocated.

Example:

```java
Employee e = new Employee();
```

Conceptually:

```text
Stack                  Heap

e ──────────────────> Employee object
                         |
                         | name
                         | age
```

The reference variable and object are conceptually different things.

Don't oversimplify this to:

> "Objects are always on the heap and references are always on the stack."

The JVM specification defines memory areas abstractly, and JVM implementations can optimize storage. For interview purposes, the conventional model is useful, but know the distinction.

---

# 15. Stack

Each thread has its own JVM stack.

Every method invocation creates a **stack frame**.

Example:

```java
public static void main(String[] args) {
    int x = 10;
    calculate();
}

static void calculate() {
    int y = 20;
}
```

Conceptually:

```text
Thread Stack

calculate() frame
-----------------
y = 20
-----------------

main() frame
-----------------
x = 10
-----------------
```

When `calculate()` returns, its stack frame is removed.

### Important interview point

The stack is:

* Thread-specific
* Used for method execution
* Contains stack frames
* Typically stores local variables and operand-stack information

The heap is:

* Shared among threads
* Used for object allocation

---

# 16. What Happens in Memory?

Consider:

```java
public class Demo {

    static int count = 10;

    int value = 20;

    public static void main(String[] args) {

        Demo obj = new Demo();

        int x = 30;
    }
}
```

Conceptually:

```text
             JVM

        Class Metadata
              |
        static count = 10

        Heap
        ┌─────────────┐
        │ Demo object │
        │ value = 20  │
        └─────────────┘
              ↑
              │
Stack         │
──────────────│──────
main frame    │
obj ──────────┘
x = 30
```

The exact physical implementation can differ, but this is the model expected in most interviews.

---

# 17. Garbage Collection

Java automatically manages memory using the **Garbage Collector (GC)**.

An object becomes eligible for garbage collection when it is no longer reachable through references from GC roots.

Example:

```java
Employee e = new Employee();

e = null;
```

The previously referenced Employee object may now become eligible for GC, assuming there are no other references to it.

### Important:

**Eligible for GC ≠ immediately garbage collected.**

You don't control exactly when the JVM performs collection.

---

# 18. Mark-Sweep GC

A simplified garbage collection algorithm is **Mark-Sweep**.

### Phase 1 — Mark

The GC identifies reachable objects.

```text
Root → A → B

C
D
```

`A` and `B` are reachable.

`C` and `D` are not.

### Phase 2 — Sweep

Unreachable objects are reclaimed.

```text
Before:

A B C D

After:

A B
```

A simple mark-sweep approach can result in **memory fragmentation**.

---

# 19. Generational Garbage Collection

Most Java GC implementations use generational concepts because of the **generational hypothesis**:

> Most objects die young.

The heap is therefore conceptually divided into generations.

```text
Heap
│
├── Young Generation
│   ├── Eden
│   ├── Survivor
│   └── Survivor
│
└── Old Generation
```

New objects are generally allocated in the young generation.

Objects that survive enough GC cycles may eventually be promoted to the old generation.

### Why?

Because short-lived objects are extremely common:

```java
for (...) {
    String temp = ...;
}
```

Collecting young objects frequently can be more efficient than treating the entire heap as one uniform region.

---

# 20. Minor GC vs Major/Full GC

Terminology varies somewhat between collectors, so don't make overly rigid claims.

Generally:

### Minor/Young GC

Primarily involves the young generation.

Usually:

* More frequent
* Faster
* Deals with many short-lived objects

### Major/Old GC

Typically refers to collection involving the old generation.

### Full GC

Can involve a much larger portion of the heap and is generally more expensive.

The exact behavior depends on the garbage collector being used.

---

# High-Value Interview Questions

These are the questions I would **definitely prepare verbally** today.

### Q1. Why is String immutable?

**Answer:**

String immutability provides several benefits, including safe String Pool sharing, security, thread safety, and stable hash codes. Since a String's state cannot change after creation, multiple references can safely point to the same pooled String, and Strings can reliably be used as keys in hash-based collections.

---

### Q2. What is the difference between `==` and `equals()`?

**Answer:**

For primitives, `==` compares values. For objects, `==` compares references. `equals()` is used for logical equality and can be overridden by a class to define what it means for two objects to be equal.

---

### Q3. Why must we override `hashCode()` when overriding `equals()`?

**Answer:**

Because Java requires equal objects to have equal hash codes. Hash-based collections such as `HashMap` and `HashSet` use hash codes to locate buckets and `equals()` to determine equality. Overriding only `equals()` can therefore cause logically equal objects to behave incorrectly in these collections.

---

### Q4. Why can't a static method directly access an instance variable?

**Answer:**

A static method belongs to the class and can be invoked without creating an object. An instance variable belongs to a particular object, so there is no specific instance from which the static method could obtain that variable.

---

### Q5. Where are objects stored in Java?

**Answer:**

In the conventional JVM memory model, objects and arrays are allocated on the heap. References to those objects may be held in stack frames, object fields, or other locations depending on context. The JVM specification does not require a particular physical implementation, and JIT optimizations can change how storage is actually realized.

---

### Q6. What makes an object eligible for garbage collection?

**Answer:**

An object becomes eligible for garbage collection when it is no longer reachable from any GC root through a chain of references.

---

### Q7. Does `System.gc()` guarantee garbage collection?

**Answer:**

No.

```java
System.gc();
```

only requests that the JVM perform garbage collection. It does not guarantee that GC will happen immediately or at all.

---

### Q8. What is the difference between String, StringBuilder and StringBuffer?

**Answer:**

`String` is immutable. `StringBuilder` and `StringBuffer` are mutable. `StringBuilder` is generally preferred for single-threaded string manipulation because it does not synchronize its methods. `StringBuffer` provides synchronized methods and is designed for thread-safe use.

---

### Q9. What is autoboxing?

**Answer:**

Autoboxing is the automatic conversion of a primitive value into its corresponding wrapper object.

```java
int x = 10;
Integer y = x;
```

The compiler conceptually converts this to something equivalent to:

```java
Integer y = Integer.valueOf(x);
```

---

### Q10. What happens when we do this?

```java
String s1 = "hello";
String s2 = new String("hello");
```

**Answer:**

The literal `"hello"` is placed in the String Pool if it isn't already present. `new String("hello")` creates a separate String object. Therefore:

```java
s1 == s2       // false
s1.equals(s2)  // true
```

---

# ⭐ The 5 Questions You Should Be Able to Draw on a Whiteboard

For product-company interviews, don't just memorize definitions. Practice explaining these with diagrams:

### 1. String Pool

```text
String a = "Java";
String b = "Java";

a ─────┐
       ├──> "Java"
b ─────┘
```

### 2. `new String()`

```text
String a = "Java";
String b = new String("Java");

a ──> Pool String
           
b ──> Separate String object
```

### 3. Stack vs Heap

```text
Thread Stack                Heap

main frame                  Employee
----------------            ----------------
obj ─────────────────────>  name
x = 10                      age
----------------            ----------------
```

### 4. HashMap lookup

```text
key
 ↓
hashCode()
 ↓
bucket
 ↓
equals()
 ↓
matching key
```

### 5. Generational GC

```text
              Heap
               |
       -----------------
       |               |
     Young            Old
       |
   ----------
   |   |    |
 Eden S1    S2
```

## Today's priority

Don't try to memorize all of this word-for-word. For your interview prep, the **highest-value topics today are**:

**`String` → `==` vs `equals()` → `hashCode()` contract → wrapper caching/autoboxing → static vs instance → stack/heap → GC.**

Those topics generate a lot of follow-up questions, especially the classic **"what exactly happens in memory?"** questions.
