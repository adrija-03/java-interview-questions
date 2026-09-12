# Day 2 — Java OOP + SOLID + Basic System Design

Today’s goal is not just to **define OOP concepts**. For product-company interviews, you should be able to take a real problem, identify objects, assign responsibilities, and explain **why your design follows OOP principles**.

---

# 1. The 4 Pillars of OOP

The four pillars are:

1. **Encapsulation**
2. **Abstraction**
3. **Inheritance**
4. **Polymorphism**

A strong interview answer should explain both **what** the concept means and **why it is useful**.

---

# 2. Encapsulation

## Definition

**Encapsulation is the practice of bundling data and the methods that operate on that data within a class, while restricting direct access to the internal state.**

In Java, encapsulation is commonly implemented using:

* `private` fields
* public/protected methods
* controlled access through methods

### Example

```java
class BankAccount {

    private double balance;

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public double getBalance() {
        return balance;
    }
}
```

The caller cannot directly do:

```java
account.balance = -10000; // Compile error
```

Instead, access is controlled through `deposit()` and `getBalance()`.

### Why encapsulation?

It provides:

* Data protection
* Controlled modification
* Reduced coupling
* Easier maintenance
* Better validation

---

## Interview Question

### Is encapsulation just making variables private?

**Answer:**

No.

`private` access is an important mechanism used to achieve encapsulation, but encapsulation is broader. It means controlling how an object's internal state is accessed and modified by exposing a well-defined public interface.

---

# 3. Abstraction

## Definition

**Abstraction means exposing the essential behavior of an object while hiding unnecessary implementation details.**

For example, when you use:

```java
payment.processPayment();
```

you don't need to know the internal steps involving:

* authentication
* network calls
* transaction processing
* database updates

You only interact with the required interface.

---

# 4. Abstract Class

An abstract class can contain:

* Abstract methods
* Concrete methods
* Instance variables
* Constructors
* Static members
* Final methods

Example:

```java
abstract class Vehicle {

    protected String brand;

    Vehicle(String brand) {
        this.brand = brand;
    }

    abstract void start();

    void displayBrand() {
        System.out.println(brand);
    }
}
```

Subclass:

```java
class Car extends Vehicle {

    Car(String brand) {
        super(brand);
    }

    @Override
    void start() {
        System.out.println("Car starts with key");
    }
}
```

---

# 5. Interface

An interface defines a contract that implementing classes must follow.

```java
interface Payment {

    void pay(double amount);
}
```

Implementation:

```java
class CreditCardPayment implements Payment {

    @Override
    public void pay(double amount) {
        System.out.println("Paid using credit card");
    }
}
```

---

# 6. Abstract Class vs Interface

This is a **very common interview question.**

| Feature              | Abstract Class      | Interface                                                                                     |
| -------------------- | ------------------- | --------------------------------------------------------------------------------------------- |
| Methods              | Abstract + concrete | Abstract + `default`/`static` + private methods in modern Java                                |
| Instance variables   | Yes                 | No instance fields                                                                            |
| Constructors         | Yes                 | No                                                                                            |
| Multiple inheritance | ❌                   | A class can implement multiple interfaces                                                     |
| Instance state       | Can maintain state  | Cannot maintain per-object instance state                                                     |
| `static` methods     | Yes                 | Yes                                                                                           |
| `final` methods      | Yes                 | Interface methods can be `default`, `static`, or abstract; a default method can be overridden |
| Relationship         | "is-a" base class   | Contract/capability                                                                           |

### When should you use an abstract class?

Use an abstract class when related classes share:

* Common state
* Common implementation
* Common base behavior

Example:

```text
Vehicle
 ├── Car
 ├── Bike
 └── Truck
```

### When should you use an interface?

Use an interface when you want to define a **capability/contract** that potentially unrelated classes can implement.

```text
Payment
 ├── CreditCardPayment
 ├── UPIPayment
 └── PayPalPayment
```

---

# 7. Java 8 Interface `default` Methods

Before Java 8, interfaces primarily contained abstract method declarations.

Java 8 introduced `default` methods so interfaces could provide implementations without breaking existing implementations.

```java
interface Vehicle {

    void start();

    default void stop() {
        System.out.println("Vehicle stopped");
    }
}
```

A class can use the default implementation:

```java
class Car implements Vehicle {

    @Override
    public void start() {
        System.out.println("Car started");
    }
}
```

Or override it:

```java
@Override
public void stop() {
    System.out.println("Car stopped");
}
```

---

# 8. Interface `static` Methods

Interfaces can also have static methods.

```java
interface MathUtil {

    static int square(int x) {
        return x * x;
    }
}
```

Call it using the interface:

```java
MathUtil.square(5);
```

You **cannot** call it through an implementing object:

```java
MathUtil obj = ...;

obj.square(5); // Invalid
```

Interface static methods belong to the interface itself.

---

# 9. What if Two Interfaces Have the Same Default Method?

Very common interview trap.

```java
interface A {

    default void show() {
        System.out.println("A");
    }
}

interface B {

    default void show() {
        System.out.println("B");
    }
}
```

Now:

```java
class C implements A, B {
}
```

This causes a compile-time error because Java cannot determine which default implementation to use.

The class must resolve the conflict:

```java
class C implements A, B {

    @Override
    public void show() {
        A.super.show();
    }
}
```

---

# 10. Inheritance

## Definition

**Inheritance allows a class to acquire properties and behavior from another class.**

Example:

```java
class Animal {

    void eat() {
        System.out.println("Eating");
    }
}

class Dog extends Animal {

    void bark() {
        System.out.println("Barking");
    }
}
```

Now:

```java
Dog dog = new Dog();

dog.eat();
dog.bark();
```

`Dog` inherits `eat()` from `Animal`.

---

# 11. Types of Inheritance in Java

Java supports:

### Single inheritance

```text
Animal
   ↓
 Dog
```

### Multilevel inheritance

```text
Animal
   ↓
Mammal
   ↓
Dog
```

### Hierarchical inheritance

```text
       Animal
       /    \
     Dog    Cat
```

Java does **not** support multiple inheritance through classes.

This is invalid:

```java
class C extends A, B { } // Invalid
```

However, Java supports multiple inheritance of **type/contracts through interfaces**:

```java
class C implements A, B {
}
```

---

# 12. `super` Keyword

`super` refers to the immediate parent class portion of the current object.

It is commonly used for:

### Accessing parent fields

```java
class Animal {
    String name = "Animal";
}

class Dog extends Animal {

    String name = "Dog";

    void print() {
        System.out.println(name);
        System.out.println(super.name);
    }
}
```

Output:

```text
Dog
Animal
```

### Calling parent method

```java
super.display();
```

### Calling parent constructor

```java
super();
```

---

# 13. Constructor Chaining

Constructor chaining means one constructor invokes another constructor.

### Within the same class

Use:

```java
this()
```

Example:

```java
class Employee {

    String name;
    int age;

    Employee() {
        this("Unknown", 0);
    }

    Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```

### Parent constructor

Use:

```java
super()
```

Example:

```java
class Animal {

    Animal(String name) {
        System.out.println(name);
    }
}

class Dog extends Animal {

    Dog() {
        super("Dog");
    }
}
```

### Important rule

A constructor call using `this()` or `super()` must be the **first statement** in the constructor.

You cannot do:

```java
Dog() {
    System.out.println("Hello");
    super(); // Compile error
}
```

---

# 14. Method Overloading

**Overloading means having multiple methods with the same name but different parameter lists within the same class or inheritance hierarchy.**

```java
class Calculator {

    int add(int a, int b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }

    double add(double a, double b) {
        return a + b;
    }
}
```

The compiler determines which method to call based on the arguments.

Therefore, overloading is **compile-time polymorphism**.

---

# 15. Method Overriding

Overriding occurs when a subclass provides its own implementation of an inherited method.

```java
class Animal {

    void sound() {
        System.out.println("Animal sound");
    }
}

class Dog extends Animal {

    @Override
    void sound() {
        System.out.println("Bark");
    }
}
```

The subclass implementation replaces the inherited implementation for that object.

Overriding enables **runtime polymorphism**.

---

# 16. Overloading vs Overriding

This comparison is interview gold.

| Feature               | Overloading                            | Overriding                                |
| --------------------- | -------------------------------------- | ----------------------------------------- |
| Meaning               | Same method name, different parameters | Subclass provides new implementation      |
| Polymorphism          | Compile-time                           | Runtime                                   |
| Inheritance required? | No                                     | Yes                                       |
| Parameters            | Must differ                            | Must match                                |
| Return type           | Cannot be the only difference          | Same or covariant return type             |
| Access                | Independent                            | Cannot reduce visibility                  |
| `static`              | Can be overloaded                      | Static methods are hidden, not overridden |
| `final`               | Can be overloaded                      | Cannot be overridden                      |
| Private method        | Can be overloaded                      | Cannot be overridden                      |

---

# 17. Runtime Polymorphism

Consider:

```java
Animal animal = new Dog();

animal.sound();
```

The **reference type** is:

```text
Animal
```

but the actual object is:

```text
Dog
```

At runtime, Java invokes:

```java
Dog.sound()
```

not:

```java
Animal.sound()
```

This is **dynamic method dispatch**.

---

# 18. Dynamic Method Dispatch

### Definition

**Dynamic method dispatch is the mechanism by which Java determines at runtime which overridden instance method should be executed based on the actual object type.**

Example:

```java
class Animal {

    void sound() {
        System.out.println("Animal");
    }
}

class Dog extends Animal {

    @Override
    void sound() {
        System.out.println("Dog");
    }
}

class Cat extends Animal {

    @Override
    void sound() {
        System.out.println("Cat");
    }
}
```

Now:

```java
Animal animal;

animal = new Dog();
animal.sound();

animal = new Cat();
animal.sound();
```

Output:

```text
Dog
Cat
```

The same reference type:

```java
Animal
```

can point to different objects, and the overridden method is selected according to the actual object's runtime type.

---

# 19. Compile-Time vs Runtime Polymorphism

### Compile-time polymorphism

Achieved primarily through **method overloading**.

```java
add(10, 20);
add(10, 20, 30);
```

The compiler resolves the method.

### Runtime polymorphism

Achieved through **method overriding** and dynamic dispatch.

```java
Animal a = new Dog();
a.sound();
```

The JVM dispatches to the overridden method associated with the actual object.

---

# 20. Important Polymorphism Trap

Consider:

```java
class Parent {

    int x = 10;

    void show() {
        System.out.println("Parent");
    }
}

class Child extends Parent {

    int x = 20;

    @Override
    void show() {
        System.out.println("Child");
    }
}
```

Now:

```java
Parent obj = new Child();

System.out.println(obj.x);
obj.show();
```

Output:

```text
10
Child
```

Why?

**Fields are not dynamically dispatched like overridden instance methods.**

The field access is based on the reference type, while the overridden instance method is dynamically dispatched based on the object's runtime type.

This is exactly the kind of question product companies like to ask.

---

# 21. SOLID Principles

SOLID is a set of five object-oriented design principles.

```text
S → Single Responsibility Principle
O → Open/Closed Principle
L → Liskov Substitution Principle
I → Interface Segregation Principle
D → Dependency Inversion Principle
```

---

# 22. S — Single Responsibility Principle

> A class should have one responsibility and therefore one reason to change.

Bad:

```java
class Employee {

    void calculateSalary() {}

    void saveToDatabase() {}

    void generateReport() {}

    void sendEmail() {}
}
```

This class has too many responsibilities.

Better:

```text
Employee
SalaryCalculator
EmployeeRepository
ReportGenerator
EmailService
```

Each class has a focused responsibility.

### Interview wording

> SRP does not mean a class should have only one method. It means the class should have one cohesive responsibility or one primary reason to change.

---

# 23. O — Open/Closed Principle

> Software entities should be open for extension but closed for modification.

Suppose we have:

```java
interface PaymentMethod {

    void pay(double amount);
}
```

Implementations:

```java
class CardPayment implements PaymentMethod {
    public void pay(double amount) {}
}

class UPIPayment implements PaymentMethod {
    public void pay(double amount) {}
}
```

If we add:

```java
class WalletPayment implements PaymentMethod {
    public void pay(double amount) {}
}
```

we can extend behavior without modifying existing payment implementations.

---

# 24. L — Liskov Substitution Principle

> Objects of a subclass should be usable wherever objects of the parent type are expected without breaking the correctness of the program.

Classic example:

```text
Bird
 ├── Sparrow
 └── Penguin
```

If `Bird` contains:

```java
void fly()
```

then `Penguin` becomes problematic because it cannot fulfill the expected flying behavior.

This indicates that the abstraction is poorly designed.

A better model could be:

```text
Bird
   |
   +---- FlyingBird
   |       |
   |     Sparrow
   |
   +---- Penguin
```

### Interview wording

LSP is fundamentally about **behavioral substitutability**, not merely inheritance syntax.

---

# 25. I — Interface Segregation Principle

> Clients should not be forced to depend on methods they do not use.

Bad:

```java
interface Worker {

    void work();

    void eat();

    void sleep();
}
```

Imagine a machine implementing this interface. A machine doesn't need `eat()` or `sleep()`.

Better:

```java
interface Workable {
    void work();
}

interface Eatable {
    void eat();
}
```

Now classes implement only the contracts they actually need.

---

# 26. D — Dependency Inversion Principle

> High-level modules should not depend directly on low-level concrete implementations. Both should depend on abstractions.

Bad:

```java
class OrderService {

    private MySQLDatabase database = new MySQLDatabase();
}
```

`OrderService` is tightly coupled to MySQL.

Better:

```java
interface Database {
    void save();
}
```

```java
class MySQLDatabase implements Database {

    public void save() {
        // MySQL implementation
    }
}
```

```java
class OrderService {

    private final Database database;

    OrderService(Database database) {
        this.database = database;
    }
}
```

Now:

```java
Database db = new MySQLDatabase();

OrderService service = new OrderService(db);
```

This is **dependency injection**, which is a common way of implementing dependency inversion.

---

# 27. Parking Lot — OOP Design

This is where you need to move from **"I know OOP"** to **"I can apply OOP."**

Suppose the interviewer says:

> Design a parking lot using OOP.

Don't immediately start writing classes.

First identify:

### Requirements

A parking lot should:

* Have multiple floors
* Have different parking spot types
* Support different vehicle types
* Assign appropriate spots
* Track parked vehicles
* Generate parking tickets
* Calculate parking fees

---

# 28. Identify Core Entities

We can start with:

```text
ParkingLot
ParkingFloor
ParkingSpot
Vehicle
Ticket
Payment
```

Then specialize where necessary.

```text
Vehicle
 ├── Car
 ├── Bike
 └── Truck
```

And:

```text
ParkingSpot
 ├── CompactSpot
 ├── BikeSpot
 └── LargeSpot
```

---

# 29. Basic Class Design

### Vehicle

```java
abstract class Vehicle {

    protected String licenseNumber;

    public Vehicle(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }
}
```

### Car

```java
class Car extends Vehicle {

    public Car(String licenseNumber) {
        super(licenseNumber);
    }
}
```

### ParkingSpot

```java
abstract class ParkingSpot {

    protected int id;
    protected boolean occupied;
    protected Vehicle vehicle;

    public ParkingSpot(int id) {
        this.id = id;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void park(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.occupied = true;
    }

    public void removeVehicle() {
        this.vehicle = null;
        this.occupied = false;
    }

    public abstract boolean canFit(Vehicle vehicle);
}
```

---

# 30. Parking Floor

```java
class ParkingFloor {

    private final int floorNumber;
    private final List<ParkingSpot> spots;

    public ParkingFloor(int floorNumber, List<ParkingSpot> spots) {
        this.floorNumber = floorNumber;
        this.spots = spots;
    }

    public ParkingSpot findAvailableSpot(Vehicle vehicle) {

        for (ParkingSpot spot : spots) {

            if (!spot.isOccupied() && spot.canFit(vehicle)) {
                return spot;
            }
        }

        return null;
    }
}
```

---

# 31. Parking Lot

```java
class ParkingLot {

    private final List<ParkingFloor> floors;

    public ParkingLot(List<ParkingFloor> floors) {
        this.floors = floors;
    }

    public ParkingSpot parkVehicle(Vehicle vehicle) {

        for (ParkingFloor floor : floors) {

            ParkingSpot spot = floor.findAvailableSpot(vehicle);

            if (spot != null) {
                spot.park(vehicle);
                return spot;
            }
        }

        return null;
    }
}
```

This is already demonstrating:

* Encapsulation
* Abstraction
* Inheritance
* Polymorphism
* Composition
* Dependency injection through constructors

---

# 32. How to Explain This Design in an Interview

Don't just show the code.

Say something like:

> "I would model Vehicle and ParkingSpot as abstractions because there are multiple types of vehicles and spots. ParkingLot owns multiple ParkingFloors, and each floor manages its own spots. I would keep the state private and expose behavior through methods to maintain encapsulation. Spot compatibility can be handled polymorphically through `canFit()`, allowing different spot types to implement their own rules. The design also keeps responsibilities separated so that parking allocation, payment, and ticket management can evolve independently."

That answer demonstrates **design thinking**, not just Java syntax.

---

# 33. OOP Interview Rapid-Fire Questions

You should be able to answer these without hesitation.

### Q1. Can we instantiate an abstract class?

No.

```java
abstract class Animal {}

Animal a = new Animal(); // Compile error
```

But an abstract class can have a constructor, and that constructor executes as part of subclass construction.

---

### Q2. Can an abstract class have a constructor?

**Yes.**

```java
abstract class Animal {

    Animal() {
        System.out.println("Animal constructor");
    }
}
```

It is called when a subclass object is created.

---

### Q3. Can an interface have a constructor?

**No.**

Interfaces cannot be instantiated and therefore do not have constructors.

---

### Q4. Can an abstract class have zero abstract methods?

**Yes.**

A class can be declared `abstract` even if it contains no abstract methods. This prevents direct instantiation and allows the class to serve as a base class.

---

### Q5. Can we override a static method?

**No.**

Static methods are associated with the class. If a subclass declares a static method with the same signature, it is **method hiding**, not overriding.

---

### Q6. Can we override a private method?

**No.**

Private methods are not accessible to subclasses and therefore are not inherited in the sense required for overriding.

---

### Q7. Can a final method be overridden?

No.

```java
final void display() {}
```

A subclass cannot override it.

---

### Q8. Can a final class be inherited?

No.

```java
final class A {}
```

```java
class B extends A {} // Compile error
```

---

### Q9. Can an interface extend another interface?

Yes.

```java
interface A {}

interface B extends A {}
```

An interface can also extend multiple interfaces:

```java
interface C extends A, B {}
```

---

### Q10. Can a class implement multiple interfaces?

Yes.

```java
class MyClass implements A, B, C {
}
```

This is one way Java supports multiple inheritance of type/contracts.

---

# 34. The Classic Interview Trap

What is the output?

```java
class Parent {

    Parent() {
        show();
    }

    void show() {
        System.out.println("Parent");
    }
}

class Child extends Parent {

    private int x = 10;

    Child() {
        x = 20;
    }

    @Override
    void show() {
        System.out.println(x);
    }
}
```

```java
Child obj = new Child();
```

What happens?

The answer is **not `20`**.

During construction:

```text
new Child()
     ↓
Child constructor starts
     ↓
implicit super()
     ↓
Parent constructor
     ↓
Parent constructor calls show()
     ↓
Child.show() executes
```

But the Child's instance field initialization has **not completed yet**.

So `x` has its default value:

```text
0
```

Therefore it prints:

```text
0
```

### Interview lesson

Avoid calling overridable methods from constructors. The subclass may not yet be fully initialized.

This is a very good **"what happens in memory/execution?"** question.

---

# 35. Your Day 2 Must-Know Summary

Before moving to Day 3, you should be able to explain these **without notes**:

```text
ENCAPSULATION
    ↓
Hide internal state + controlled access

ABSTRACTION
    ↓
Expose essential behavior + hide implementation

INHERITANCE
    ↓
Reuse/extend behavior from a parent type

POLYMORPHISM
    ↓
Same interface/reference → different behavior
```

And especially:

```text
Overloading
    ↓
Compile time

Overriding
    ↓
Runtime
    ↓
Dynamic Method Dispatch
```

```text
Abstract Class
    ↓
Shared state + implementation + abstraction

Interface
    ↓
Contract / capability
    ↓
Multiple interfaces possible
```

```text
SOLID
S → One responsibility
O → Extend without modifying existing behavior
L → Subtypes must remain substitutable
I → Small, focused interfaces
D → Depend on abstractions, not concrete implementations
```
