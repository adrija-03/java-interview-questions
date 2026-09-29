# OOP — The 4 Pillars in Java

Object-Oriented Programming (OOP) is a programming paradigm where software is designed around **objects**, which contain:

* **State** → data/attributes
* **Behavior** → methods/functions

The four fundamental principles of OOP are:

1. **Encapsulation**
2. **Abstraction**
3. **Inheritance**
4. **Polymorphism**

---

# 1. Encapsulation

## Purpose

Encapsulation is used to:

* Protect an object's internal state.
* Prevent uncontrolled modification of data.
* Control how data is accessed or modified.
* Keep data and the methods operating on that data together.
* Maintain valid object state.

The key idea is:

> **"Who can access or modify this data, and how?"**

---

## Definition

> **Encapsulation is the process of bundling data and the methods that operate on that data into a single unit, while restricting direct access to the internal state of the object.**

In Java, encapsulation is commonly achieved using:

* `private` fields
* `public`/controlled methods
* Access modifiers

---

## Java Syntax

```java
class BankAccount {

    private double balance;

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }
}
```

The important part is:

```java
private double balance;
```

External code cannot directly modify it.

---

## Example

```java
BankAccount account = new BankAccount();

account.deposit(1000);

System.out.println(account.getBalance());
```

This is not allowed:

```java
account.balance = -500; // ❌
```

because `balance` is private.

Instead:

```java
account.deposit(500); // ✅
```

The class controls how its state changes.

---

## Rules

1. Data that should not be directly accessible should generally be `private`.
2. Provide controlled methods for accessing or modifying state.
3. Validation should happen inside the class when appropriate.
4. Don't blindly generate getters/setters for every field.
5. Encapsulation is about **control**, not simply getters and setters.
6. Access modifiers are an important tool for implementing encapsulation.

---

## Real-world Use

### Bank Account

A bank account should not allow:

```java
account.balance = -100000;
```

Instead, operations such as:

```java
deposit()
withdraw()
transfer()
```

control how the balance changes.

---

## LLD Use

Encapsulation is used almost everywhere in LLD.

Example:

```java
class ParkingSpot {

    private boolean occupied;

    public boolean isOccupied() {
        return occupied;
    }

    public void park() {
        if (!occupied) {
            occupied = true;
        }
    }

    public void removeVehicle() {
        occupied = false;
    }
}
```

Other classes don't directly manipulate:

```java
occupied
```

They use the object's behavior.

This protects the object's state.

---

## Interview Questions

### Q1. What is encapsulation?

> Encapsulation is bundling data and the methods that operate on it together while controlling access to the object's internal state.

### Q2. How do you achieve encapsulation in Java?

> Primarily using classes, private fields, access modifiers, and controlled methods such as getters, setters, or domain-specific methods.

### Q3. Is encapsulation the same as data hiding?

Not exactly.

**Data hiding** focuses on restricting access to internal data.

**Encapsulation** is the broader concept of bundling state and behavior together while controlling access.

### Q4. Why shouldn't all fields simply be public?

Because any code could modify the object's state without validation or control.

### Q5. Is using getters and setters always good encapsulation?

No.

This:

```java
account.setBalance(-5000);
```

may actually weaken the design.

A better API might be:

```java
account.withdraw(5000);
```

because the object controls the business rules.

---

## Code

```java
class BankAccount {

    private double balance;

    public BankAccount(double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException(
                "Initial balance cannot be negative"
            );
        }

        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                "Deposit must be positive"
            );
        }

        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            throw new IllegalArgumentException(
                "Invalid withdrawal"
            );
        }

        balance -= amount;
    }
}
```

---

# 2. Abstraction

## Purpose

Abstraction is used to:

* Hide unnecessary implementation details.
* Expose only essential behavior.
* Reduce complexity.
* Reduce coupling.
* Define clear contracts between components.

The key idea is:

> **"What can this object do without exposing how it does it?"**

---

## Definition

> **Abstraction is the process of hiding implementation details and exposing only the essential behavior of an object through a well-defined interface or abstract class.**

Java provides abstraction mainly through:

* Abstract classes
* Interfaces

---

## Java Syntax

### Abstract Class

```java
abstract class Vehicle {

    abstract void start();

    void stop() {
        System.out.println("Vehicle stopped");
    }
}
```

### Interface

```java
interface Payment {

    void pay(double amount);
}
```

---

## Example

```java
interface Payment {

    void pay(double amount);
}
```

Implementation:

```java
class UpiPayment implements Payment {

    @Override
    public void pay(double amount) {
        System.out.println(
            "Processing UPI payment"
        );
    }
}
```

The caller only needs to know:

```java
Payment payment = new UpiPayment();

payment.pay(500);
```

It doesn't need to know how UPI processing works internally.

---

## Rules

### Abstract class

1. Cannot be instantiated directly.
2. Can contain abstract methods.
3. Can contain concrete methods.
4. Can have constructors.
5. Can contain instance variables.
6. A concrete subclass must implement all abstract methods.

### Interface

1. Cannot be instantiated directly.
2. A class implements an interface using `implements`.
3. A class can implement multiple interfaces.
4. Interfaces can contain abstract methods.
5. Since Java 8, interfaces can contain `default` and `static` methods.

---

## Real-world Use

### Payment System

The application only needs:

```java
payment.pay(amount);
```

The implementation could be:

```text
Payment
   ↑
   ├── UPI
   ├── Card
   ├── Wallet
   └── NetBanking
```

The application doesn't need to know the internal implementation of each payment method.

---

## LLD Use

Abstraction is heavily used to create **contracts between classes**.

Example:

```java
interface PaymentMethod {

    void pay(double amount);
}
```

Then:

```java
class CardPayment implements PaymentMethod {
    // implementation
}

class UpiPayment implements PaymentMethod {
    // implementation
}
```

A service can depend on:

```java
PaymentMethod
```

rather than a specific implementation.

This reduces coupling and makes adding new payment methods easier.

---

## Interview Questions

### Q1. What is abstraction?

> Abstraction hides implementation complexity and exposes only essential behavior.

### Q2. How is abstraction achieved in Java?

> Primarily through interfaces and abstract classes.

### Q3. Can an abstract class have concrete methods?

Yes.

### Q4. Can an abstract class have a constructor?

Yes.

### Q5. Can we instantiate an abstract class?

No.

### Q6. Interface vs abstract class?

> Use an abstract class when related classes need to share state or implementation. Use an interface when defining a contract or capability that can be implemented by different classes.

### Q7. Abstraction vs encapsulation?

> Abstraction focuses on **hiding implementation complexity**. Encapsulation focuses on **controlling access to an object's state**.

---

## Code

```java
interface PaymentMethod {

    void pay(double amount);
}
```

```java
class CardPayment implements PaymentMethod {

    @Override
    public void pay(double amount) {
        System.out.println(
            "Paid using Card: " + amount
        );
    }
}
```

```java
class UpiPayment implements PaymentMethod {

    @Override
    public void pay(double amount) {
        System.out.println(
            "Paid using UPI: " + amount
        );
    }
}
```

Usage:

```java
PaymentMethod payment = new UpiPayment();

payment.pay(1000);
```

---

# 3. Inheritance

## Purpose

Inheritance is used to:

* Represent an **IS-A** relationship.
* Reuse common behavior.
* Create hierarchical relationships.
* Allow subclasses to extend parent behavior.
* Enable runtime polymorphism.

The key idea is:

> **"What characteristics/behavior can a child inherit from a parent?"**

---

## Definition

> **Inheritance is an OOP mechanism where a child class acquires accessible properties and behavior from a parent class and can extend or override that behavior.**

Java uses:

```java
extends
```

for class inheritance.

---

## Java Syntax

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

---

## Example

```java
Dog dog = new Dog();

dog.eat();  // inherited
dog.bark(); // Dog's own method
```

Here:

```text
Dog IS-A Animal
```

---

## Rules

1. Java supports **single inheritance for classes**.
2. A class can extend only one class.
3. A class can implement multiple interfaces.
4. Constructors are not inherited.
5. Private members are not directly accessible in subclasses.
6. `super` can be used to access parent-class members.
7. A subclass can override accessible parent methods.
8. `final` classes cannot be extended.
9. `final` methods cannot be overridden.
10. Inheritance should represent a genuine **IS-A** relationship.

---

## Real-world Use

Example:

```text
Vehicle
   ↑
   ├── Car
   ├── Bike
   └── Truck
```

Common behavior can be placed in `Vehicle`.

Specific behavior can be implemented by each subclass.

---

## LLD Use

Inheritance can be useful when multiple classes genuinely share a common abstraction.

Example:

```java
abstract class Vehicle {

    protected String number;

    Vehicle(String number) {
        this.number = number;
    }

    abstract void drive();
}
```

Then:

```java
class Car extends Vehicle {

    Car(String number) {
        super(number);
    }

    @Override
    void drive() {
        System.out.println("Car driving");
    }
}
```

However:

> **Don't use inheritance just for code reuse.**

If the relationship is:

```text
Car HAS-A Engine
```

then inheritance is wrong.

Use composition:

```java
class Car {

    private Engine engine;
}
```

---

## Interview Questions

### Q1. What is inheritance?

> Inheritance allows a child class to acquire accessible behavior and properties from a parent class and extend or override them.

### Q2. Why does Java not support multiple inheritance with classes?

One major reason is to avoid ambiguity such as the **diamond problem**.

Java instead allows:

```java
class MyClass implements A, B
```

with multiple interfaces.

### Q3. What is IS-A?

Inheritance represents an IS-A relationship.

```text
Dog IS-A Animal
Car IS-A Vehicle
```

### Q4. What is HAS-A?

Composition/aggregation represents HAS-A.

```text
Car HAS-A Engine
Library HAS-A Books
```

### Q5. Are constructors inherited?

No.

### Q6. What is `super`?

`super` refers to the immediate parent class and can be used to access parent constructors, methods, and accessible fields.

### Q7. When should you avoid inheritance?

When the relationship is not genuinely IS-A or when composition provides a more flexible design.

---

## Code

```java
class Vehicle {

    protected String brand;

    Vehicle(String brand) {
        this.brand = brand;
    }

    void start() {
        System.out.println("Vehicle started");
    }
}
```

```java
class Car extends Vehicle {

    Car(String brand) {
        super(brand);
    }

    @Override
    void start() {
        System.out.println(
            brand + " car started"
        );
    }
}
```

Usage:

```java
Car car = new Car("Toyota");

car.start();
```

---

# 4. Polymorphism

## Purpose

Polymorphism allows the same interface or method call to behave differently depending on the object involved.

The key idea is:

> **"One interface, multiple implementations."**

It helps create:

* Flexible code
* Extensible systems
* Loose coupling
* Runtime behavior selection

---

## Definition

> **Polymorphism is the ability of one interface, reference, or method to represent or operate on objects of different forms.**

Java has two major forms:

1. **Compile-time polymorphism**
2. **Runtime polymorphism**

---

# 4.1 Compile-Time Polymorphism

Usually achieved through **method overloading**.

Same method name:

```java
calculate()
```

but different parameters.

```java
class Calculator {

    int add(int a, int b) {
        return a + b;
    }

    double add(double a, double b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }
}
```

The compiler determines which method to call based on the arguments.

---

# 4.2 Runtime Polymorphism

Achieved through **method overriding**.

```java
class Animal {

    void sound() {
        System.out.println("Animal sound");
    }
}
```

```java
class Dog extends Animal {

    @Override
    void sound() {
        System.out.println("Bark");
    }
}
```

Now:

```java
Animal animal = new Dog();

animal.sound();
```

Output:

```text
Bark
```

The reference type is:

```java
Animal
```

but the actual object is:

```java
Dog
```

Therefore, Java calls:

```java
Dog.sound()
```

This is **runtime polymorphism / dynamic method dispatch**.

---

## Rules

### Method Overloading

1. Same method name.
2. Different parameter list.
3. Can differ by number/type/order of parameters.
4. Return type alone cannot distinguish overloaded methods.
5. Resolved at compile time.

### Method Overriding

1. Requires inheritance or interface implementation.
2. Same method signature.
3. Child provides a different implementation.
4. Resolved at runtime.
5. Use `@Override`.
6. Cannot reduce visibility.
7. `final` methods cannot be overridden.
8. `static` methods are hidden, not overridden in the normal runtime-polymorphism sense.

---

## Real-world Use

### Payment System

```text
PaymentMethod
      ↑
 ┌────┼────────┐
UPI  Card    Wallet
```

You can write:

```java
PaymentMethod payment;
```

Then assign:

```java
payment = new UpiPayment();
```

or:

```java
payment = new CardPayment();
```

The same call:

```java
payment.pay(1000);
```

can execute different implementations.

---

## LLD Use

Polymorphism is one of the most important tools in LLD.

Example:

```java
interface Notification {

    void send(String message);
}
```

Implementations:

```java
class EmailNotification implements Notification {

    @Override
    public void send(String message) {
        System.out.println("Sending Email");
    }
}
```

```java
class SmsNotification implements Notification {

    @Override
    public void send(String message) {
        System.out.println("Sending SMS");
    }
}
```

Now:

```java
class NotificationService {

    private Notification notification;

    NotificationService(Notification notification) {
        this.notification = notification;
    }

    void notifyUser(String message) {
        notification.send(message);
    }
}
```

We can pass:

```java
new EmailNotification()
```

or:

```java
new SmsNotification()
```

without changing `NotificationService`.

This is a core LLD principle:

> **Program to an abstraction, not a concrete implementation.**

---

## Interview Questions

### Q1. What is polymorphism?

> Polymorphism allows the same interface or method call to behave differently depending on the object or parameters involved.

### Q2. What are the types of polymorphism in Java?

> Compile-time polymorphism through method overloading and runtime polymorphism through method overriding.

### Q3. Overloading vs overriding?

|              | Overloading                    | Overriding               |
| ------------ | ------------------------------ | ------------------------ |
| When         | Compile time                   | Runtime                  |
| Relationship | Usually same class             | Parent-child/interface   |
| Parameters   | Must differ                    | Same                     |
| Return type  | Cannot alone distinguish       | Same or covariant        |
| Purpose      | Multiple ways to call a method | Different implementation |

### Q4. What is dynamic method dispatch?

> It is the mechanism by which Java determines the overridden method to execute at runtime based on the actual object rather than the reference type.

Example:

```java
Animal a = new Dog();

a.sound();
```

`Dog.sound()` executes.

### Q5. Can static methods be overridden?

No. Static methods are associated with the class and are **hidden**, not overridden through runtime polymorphism.

### Q6. Can private methods be overridden?

No. Private methods are not accessible to subclasses and therefore cannot be overridden.

---

## Code

```java
interface PaymentMethod {

    void pay(double amount);
}
```

```java
class UpiPayment implements PaymentMethod {

    @Override
    public void pay(double amount) {
        System.out.println(
            "Paying " + amount + " using UPI"
        );
    }
}
```

```java
class CardPayment implements PaymentMethod {

    @Override
    public void pay(double amount) {
        System.out.println(
            "Paying " + amount + " using Card"
        );
    }
}
```

Usage:

```java
public class Main {

    public static void main(String[] args) {

        PaymentMethod payment;

        payment = new UpiPayment();
        payment.pay(500);

        payment = new CardPayment();
        payment.pay(1000);
    }
}
```

The caller uses the same:

```java
payment.pay();
```

but different implementations execute.

---

# 5. How the Four Pillars Work Together

The four principles are not isolated concepts.

A good LLD usually uses several together.

Consider a payment system:

```text
                    PaymentMethod
                         ↑
              ┌──────────┴──────────┐
              │                     │
        UpiPayment            CardPayment
```

### Abstraction

```java
interface PaymentMethod
```

defines what a payment method can do.

### Encapsulation

Each implementation hides its internal payment details.

### Inheritance / Implementation

```java
UpiPayment implements PaymentMethod
CardPayment implements PaymentMethod
```

creates the relationship with the abstraction.

### Polymorphism

```java
PaymentMethod payment = new UpiPayment();
```

allows the same reference to represent different implementations.

---

# 6. Quick Comparison

| Principle         | Main Idea                              | Question to Remember               |
| ----------------- | -------------------------------------- | ---------------------------------- |
| **Encapsulation** | Protect/control state                  | "How do I control access?"         |
| **Abstraction**   | Hide implementation complexity         | "What does it do?"                 |
| **Inheritance**   | Reuse/extend through IS-A relationship | "Is this a type of that?"          |
| **Polymorphism**  | One interface, multiple behaviors      | "Which implementation should run?" |

---

# 7. One-Line Interview Definitions

### Encapsulation

> Bundling data and behavior together while controlling access to the object's internal state.

### Abstraction

> Hiding implementation details and exposing only essential behavior.

### Inheritance

> A mechanism where a child class acquires accessible behavior from a parent class and can extend or override it.

### Polymorphism

> The ability of the same interface or method call to represent or invoke different implementations.

---

# 8. Most Important Interview Distinctions

## Encapsulation vs Abstraction

```text
Encapsulation
→ How do I protect/control the data?

Abstraction
→ How do I hide complexity?
```

Example:

```java
private double balance;
```

→ Encapsulation

```java
interface Payment {
    void pay();
}
```

→ Abstraction

---

## Inheritance vs Composition

```text
Inheritance
→ IS-A

Dog IS-A Animal
```

```text
Composition
→ HAS-A

Car HAS-A Engine
```

In LLD, don't use inheritance merely for code reuse. Prefer composition when the domain relationship is HAS-A.

---

## Overloading vs Overriding

```text
Overloading
→ Same name
→ Different parameters
→ Compile time
```

```text
Overriding
→ Same signature
→ Different implementation
→ Runtime
```

---

# 9. Final Revision Sheet

```text
OOP
│
├── Encapsulation
│   └── Protect/control state
│
├── Abstraction
│   └── Hide implementation complexity
│
├── Inheritance
│   └── IS-A relationship
│
└── Polymorphism
    └── One interface → multiple implementations
```

### Remember:

```text
Encapsulation → DATA
Abstraction   → COMPLEXITY
Inheritance   → RELATIONSHIP
Polymorphism  → BEHAVIOR
```

### LLD mindset

When designing a system, ask:

```text
1. What data should be protected?
       ↓
   Encapsulation

2. What implementation details should be hidden?
       ↓
   Abstraction

3. Which objects genuinely have an IS-A relationship?
       ↓
   Inheritance

4. Where can multiple implementations exist behind one contract?
       ↓
   Polymorphism
```
