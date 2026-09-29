# Composition vs Inheritance

## 1. Purpose

Both **composition** and **inheritance** are ways to establish relationships between classes.

The key difference:

```text
Inheritance  → IS-A
Composition  → HAS-A
```

Examples:

```text
Dog IS-A Animal
Car HAS-A Engine
```

In LLD, choosing between them correctly is important because inheritance creates a stronger coupling between classes, while composition generally provides more flexibility.

---

## 2. Definition

### Inheritance

> Inheritance allows a child class to acquire accessible behavior and properties from a parent class.

```java
class Car extends Vehicle {
}
```

Relationship:

```text
Car IS-A Vehicle
```

### Composition

> Composition is a relationship where one class contains or uses an object of another class to achieve its functionality.

```java
class Car {

    private Engine engine;
}
```

Relationship:

```text
Car HAS-A Engine
```

---

## 3. Java Syntax

### Inheritance

```java
class Vehicle {

    void start() {
        System.out.println("Vehicle started");
    }
}

class Car extends Vehicle {

    void drive() {
        System.out.println("Car driving");
    }
}
```

---

### Composition

```java
class Engine {

    void start() {
        System.out.println("Engine started");
    }
}

class Car {

    private Engine engine;

    Car() {
        this.engine = new Engine();
    }

    void startCar() {
        engine.start();
    }
}
```

---

## 4. Example

### Inheritance

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

Here:

```text
Dog IS-A Animal
```

So inheritance makes sense.

---

### Composition

```java
class Engine {

    void start() {
        System.out.println("Engine started");
    }
}

class Car {

    private Engine engine;

    Car() {
        this.engine = new Engine();
    }

    void start() {
        engine.start();
    }
}
```

Here:

```text
Car HAS-A Engine
```

An engine is a component of a car, not a type of car.

Therefore, composition makes sense.

---

# 5. Rules

## Inheritance Rules

1. Represents an **IS-A** relationship.
2. Uses `extends` for classes.
3. A Java class can extend only one class.
4. Child classes can override inherited methods.
5. Creates tighter coupling between parent and child.
6. Changes in the parent can affect subclasses.
7. Use inheritance when the child genuinely represents a specialized form of the parent.

---

## Composition Rules

1. Represents a **HAS-A** relationship.
2. One class contains or uses another object.
3. It promotes loose coupling.
4. Components can be replaced more easily.
5. It allows behavior to be changed by changing the contained object.
6. It is often preferred over inheritance for code reuse.

---

# 6. Real-World Use

### Inheritance

```text
Vehicle
   ↑
   ├── Car
   ├── Bike
   └── Truck
```

A car is a type of vehicle.

---

### Composition

```text
Car
 ├── Engine
 ├── Transmission
 └── Wheels
```

A car contains these components.

---

# 7. LLD Use

Consider a notification system.

### Bad approach: inheritance

```java
class EmailNotification extends Notification {
}

class SmsNotification extends Notification {
}
```

This may be fine if `Notification` is genuinely a base abstraction.

But suppose we also need:

```text
Email + SMS
Email + Push
SMS + Push
Email + SMS + Push
```

Inheritance can lead to many combinations.

---

### Better approach: composition

```java
interface NotificationSender {

    void send(String message);
}
```

Then:

```java
class EmailSender implements NotificationSender {

    public void send(String message) {
        System.out.println("Email sent");
    }
}
```

```java
class SmsSender implements NotificationSender {

    public void send(String message) {
        System.out.println("SMS sent");
    }
}
```

A notification service can contain a sender:

```java
class NotificationService {

    private NotificationSender sender;

    NotificationService(NotificationSender sender) {
        this.sender = sender;
    }

    void notify(String message) {
        sender.send(message);
    }
}
```

Now behavior can be changed without changing `NotificationService`.

This is composition + abstraction + polymorphism.

---

# 8. Composition vs Inheritance

| Feature                 | Inheritance                             | Composition               |
| ----------------------- | --------------------------------------- | ------------------------- |
| Relationship            | IS-A                                    | HAS-A                     |
| Keyword                 | `extends`                               | Object reference          |
| Coupling                | Higher                                  | Usually lower             |
| Flexibility             | Lower                                   | Higher                    |
| Code reuse              | Through parent                          | Through contained objects |
| Runtime behavior change | Limited                                 | Easier                    |
| Multiple behaviors      | Difficult with class inheritance        | Easy to combine           |
| LLD preference          | Use when relationship is genuinely IS-A | Often preferred           |
| Example                 | `Dog extends Animal`                    | `Car has Engine`          |

---

# 9. Interview Questions

### Q1. Composition vs inheritance?

> Inheritance represents an IS-A relationship, while composition represents a HAS-A relationship. Inheritance creates a tighter relationship between parent and child, while composition generally provides greater flexibility and lower coupling.

---

### Q2. Why is composition often preferred over inheritance?

Because composition:

* Reduces coupling.
* Makes behavior easier to change.
* Allows different implementations to be substituted.
* Avoids deep inheritance hierarchies.
* Makes it easier to combine multiple behaviors.

---

### Q3. Should we never use inheritance?

No.

Inheritance is appropriate when there is a genuine **IS-A relationship** and the subclass truly represents a specialized version of the parent.

---

### Q4. Give an example where inheritance is wrong.

```text
Car extends Engine
```

This is wrong because:

```text
Car IS-A Engine ❌
```

A car **HAS-A** engine.

---

### Q5. What does "favor composition over inheritance" mean?

It means that when both approaches could solve a problem, composition often gives better flexibility and lower coupling.

It does **not** mean inheritance should never be used.

---

# 10. Code

### Composition with an interface

```java
interface Engine {

    void start();
}
```

```java
class PetrolEngine implements Engine {

    @Override
    public void start() {
        System.out.println("Petrol engine started");
    }
}
```

```java
class ElectricEngine implements Engine {

    @Override
    public void start() {
        System.out.println("Electric engine started");
    }
}
```

```java
class Car {

    private Engine engine;

    Car(Engine engine) {
        this.engine = engine;
    }

    void start() {
        engine.start();
    }
}
```

Usage:

```java
Car petrolCar = new Car(
    new PetrolEngine()
);

petrolCar.start();
```

We can change the behavior:

```java
Car electricCar = new Car(
    new ElectricEngine()
);

electricCar.start();
```

The `Car` class itself doesn't change.

This is a very important LLD pattern:

```text
Car
 ↓
Engine interface
 ↓
 ├── PetrolEngine
 └── ElectricEngine
```

---

# Interface

## 1. Purpose

An interface defines a **contract** that classes can implement.

It is useful when multiple classes should provide the same behavior but may have completely different implementations.

The key idea:

> **Define what a class must do without dictating how it must do it.**

Interfaces are heavily used in LLD because they support:

* Abstraction
* Polymorphism
* Loose coupling
* Dependency Injection
* Extensibility

---

## 2. Definition

> **An interface in Java is a contract that defines behavior that implementing classes agree to provide.**

Example:

```java
interface Payment {

    void pay(double amount);
}
```

Any concrete class implementing `Payment` must provide `pay()`.

---

## 3. Java Syntax

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
        System.out.println("UPI payment");
    }
}
```

Another implementation:

```java
class CardPayment implements Payment {

    @Override
    public void pay(double amount) {
        System.out.println("Card payment");
    }
}
```

---

## 4. Example

```java
Payment payment = new UpiPayment();

payment.pay(500);
```

The reference type is:

```text
Payment
```

The actual object is:

```text
UpiPayment
```

This demonstrates both:

* Abstraction
* Runtime polymorphism

---

## 5. Rules

### Rule 1 — Use `implements`

```java
class Dog implements Animal {
}
```

---

### Rule 2 — A class can implement multiple interfaces

```java
class SmartPhone implements Camera, MusicPlayer {
}
```

This is one major advantage over class inheritance.

---

### Rule 3 — Interface methods

Traditional interface methods are implicitly:

```java
public abstract
```

For example:

```java
interface Payment {

    void pay();
}
```

is effectively:

```java
interface Payment {

    public abstract void pay();
}
```

---

### Rule 4 — Interfaces can have default methods

Since Java 8:

```java
interface Payment {

    void pay();

    default void printReceipt() {
        System.out.println("Receipt");
    }
}
```

---

### Rule 5 — Interfaces can have static methods

```java
interface Payment {

    static void validate() {
        System.out.println("Validating");
    }
}
```

Called using:

```java
Payment.validate();
```

---

### Rule 6 — Interface fields are constants

Fields declared in an interface are implicitly:

```text
public static final
```

Example:

```java
interface Payment {

    int MAX_AMOUNT = 100000;
}
```

---

# 6. Real-World Use

### Payment

```text
Payment
   ↑
 ┌─┼───────────┐
UPI Card      Wallet
```

### Notification

```text
Notification
      ↑
 ┌────┼──────┐
Email SMS   Push
```

### Storage

```text
Storage
   ↑
 ┌─┼─────────────┐
S3 Local       Database
```

The application can depend on the interface instead of a specific implementation.

---

# 7. LLD Use

Suppose we create a ride-booking application.

We could define:

```java
interface PricingStrategy {

    double calculateFare(double distance);
}
```

Then:

```java
class NormalPricing implements PricingStrategy {

    public double calculateFare(double distance) {
        return distance * 10;
    }
}
```

```java
class SurgePricing implements PricingStrategy {

    public double calculateFare(double distance) {
        return distance * 20;
    }
}
```

The ride service doesn't need to know which pricing algorithm is being used.

This allows us to change behavior without modifying the main service.

---

# 8. Interview Questions

### Q1. Why use an interface?

> To define a contract and allow multiple implementations while keeping the dependent code loosely coupled.

### Q2. Can a class implement multiple interfaces?

Yes.

### Q3. Can an interface extend another interface?

Yes.

```java
interface A {
}

interface B extends A {
}
```

An interface can also extend multiple interfaces:

```java
interface C extends A, B {
}
```

### Q4. Can an interface extend a class?

No.

### Q5. Can an interface have a constructor?

No.

### Q6. Can an interface have variables?

Yes, but interface fields are implicitly:

```text
public static final
```

### Q7. Interface vs abstract class?

> Use an interface primarily to define a contract/capability. Use an abstract class when related classes need shared state or common implementation.

---

# Dependency Injection — Basics

## 1. Purpose

Dependency Injection (DI) is used to:

* Reduce coupling.
* Make classes easier to test.
* Make implementations replaceable.
* Separate object creation from object usage.
* Support programming to abstractions.

The key idea:

> **A class should receive the objects it depends on instead of creating them itself.**

---

## 2. Definition

> **Dependency Injection is a technique where an object's dependencies are provided to it from outside rather than the object creating those dependencies itself.**

---

## 3. First Understand "Dependency"

Suppose:

```java
class Car {

    private Engine engine;

    Car() {
        engine = new PetrolEngine();
    }
}
```

`Car` depends on:

```text
PetrolEngine
```

because it creates and uses it.

This creates tight coupling.

---

# 4. Without Dependency Injection

```java
class Car {

    private PetrolEngine engine;

    Car() {
        this.engine = new PetrolEngine();
    }

    void start() {
        engine.start();
    }
}
```

Problem:

`Car` is directly coupled to `PetrolEngine`.

If we want:

```text
ElectricEngine
```

we need to modify `Car`.

---

# 5. With Dependency Injection

First define an abstraction:

```java
interface Engine {

    void start();
}
```

Implement it:

```java
class PetrolEngine implements Engine {

    @Override
    public void start() {
        System.out.println("Petrol engine started");
    }
}
```

```java
class ElectricEngine implements Engine {

    @Override
    public void start() {
        System.out.println("Electric engine started");
    }
}
```

Now inject the dependency:

```java
class Car {

    private final Engine engine;

    Car(Engine engine) {
        this.engine = engine;
    }

    void start() {
        engine.start();
    }
}
```

Usage:

```java
Engine engine = new PetrolEngine();

Car car = new Car(engine);

car.start();
```

Or:

```java
Engine engine = new ElectricEngine();

Car car = new Car(engine);

car.start();
```

`Car` doesn't care which implementation it receives.

---

# 6. Types of Dependency Injection

There are three commonly discussed types.

## 1. Constructor Injection ⭐

Dependency is provided through the constructor.

```java
class Car {

    private final Engine engine;

    Car(Engine engine) {
        this.engine = engine;
    }
}
```

This is generally the preferred form because required dependencies are available when the object is created.

---

## 2. Setter Injection

Dependency is provided through a setter.

```java
class Car {

    private Engine engine;

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
```

Useful when the dependency is optional or may change.

---

## 3. Field Injection

Dependency is directly injected into a field, commonly through a framework such as Spring.

Conceptually:

```java
class Car {

    @Autowired
    private Engine engine;
}
```

This is common in Spring codebases, but constructor injection is generally preferred for required dependencies because dependencies are explicit and the object can be immutable.

---

# 7. Rules

1. A dependency is an object another class needs to perform its work.
2. DI means the dependency comes from outside.
3. Prefer depending on abstractions rather than concrete implementations.
4. Constructor injection is generally preferred for required dependencies.
5. DI reduces coupling.
6. DI improves testability.
7. DI itself is a design technique; frameworks such as Spring can automate it.
8. DI and Dependency Inversion are related but **not the same thing**.

---

# 8. Real-World Use

Suppose an application sends notifications.

Without DI:

```java
class OrderService {

    private EmailService emailService =
        new EmailService();
}
```

Now `OrderService` is tightly coupled to email.

With DI:

```java
class OrderService {

    private final NotificationService notificationService;

    OrderService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
}
```

We can provide:

```text
EmailNotification
SMSNotification
PushNotification
```

without changing `OrderService`.

---

# 9. LLD Use

DI is extremely useful in LLD because it allows components to depend on abstractions.

Example:

```text
             OrderService
                  |
                  ↓
        PaymentMethod interface
             ↑          ↑
             |          |
       UpiPayment   CardPayment
```

`OrderService` doesn't create:

```java
new UpiPayment()
```

it receives a `PaymentMethod`.

This makes the design:

* Loosely coupled
* Testable
* Extensible
* Easier to modify

---

# 10. DI and Unit Testing

One of the biggest advantages is testing.

Suppose:

```java
class OrderService {

    private final PaymentMethod payment;

    OrderService(PaymentMethod payment) {
        this.payment = payment;
    }
}
```

During production:

```java
OrderService service =
    new OrderService(new UpiPayment());
```

During testing, we can provide a fake/mock implementation:

```java
OrderService service =
    new OrderService(mockPayment);
```

We don't need a real payment system to test `OrderService`.

This is one reason DI is so important in real-world software development.

---

# 11. Interview Questions

### Q1. What is Dependency Injection?

> Dependency Injection is a technique where an object's dependencies are provided from outside instead of being created inside the object.

### Q2. Why is DI useful?

> It reduces coupling, improves testability, makes implementations replaceable, and separates object creation from business logic.

### Q3. What is the preferred type of DI?

> Constructor injection is generally preferred for required dependencies because dependencies are explicit and the object can be immutable.

### Q4. Is DI the same as Dependency Inversion?

No.

**Dependency Injection:**

> A technique for providing dependencies from outside.

**Dependency Inversion Principle:**

> A SOLID principle stating that high-level modules should depend on abstractions rather than concrete implementations.

DI is one way to help implement the Dependency Inversion Principle.

### Q5. What is the problem with this?

```java
class OrderService {

    private PaymentService payment =
        new PaymentService();
}
```

The class is tightly coupled to a concrete implementation and is harder to test or replace.

### Q6. How does DI help testing?

You can inject a mock/fake dependency instead of using the real implementation.

---

# 12. Code — Complete Example

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
            "Paid using UPI: " + amount
        );
    }
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
class OrderService {

    private final PaymentMethod paymentMethod;

    // Constructor Injection
    OrderService(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    void placeOrder(double amount) {
        paymentMethod.pay(amount);
        System.out.println("Order placed");
    }
}
```

Usage:

```java
public class Main {

    public static void main(String[] args) {

        PaymentMethod payment =
            new UpiPayment();

        OrderService orderService =
            new OrderService(payment);

        orderService.placeOrder(1000);
    }
}
```

The important design is:

```text
OrderService
     |
     ↓
PaymentMethod
     ↑
 ┌───┴──────┐
UPI        Card
```

`OrderService` depends on the **abstraction**, not the concrete implementation.

---

# 13. How These Concepts Connect

These four concepts are frequently used together in LLD:

```text
Interface
    ↓
Abstraction
    ↓
Composition
    ↓
Dependency Injection
    ↓
Polymorphism
```

Example:

```java
interface Engine {
    void start();
}
```

```java
class Car {

    private final Engine engine;

    Car(Engine engine) {
        this.engine = engine;
    }

    void start() {
        engine.start();
    }
}
```

Here:

### Interface

```java
Engine
```

defines the contract.

### Abstraction

`Car` doesn't need to know how the engine works internally.

### Composition

```java
Car HAS-A Engine
```

### Dependency Injection

`Engine` is provided through the constructor.

### Polymorphism

```java
Engine engine = new PetrolEngine();
```

or:

```java
Engine engine = new ElectricEngine();
```

---

# 14. Final Revision Sheet

```text
INHERITANCE
    ↓
IS-A
    ↓
Car IS-A Vehicle


COMPOSITION
    ↓
HAS-A
    ↓
Car HAS-A Engine


INTERFACE
    ↓
Defines a contract
    ↓
Multiple implementations


DEPENDENCY INJECTION
    ↓
Give dependencies from outside
    ↓
Don't create them inside the class
```

### Most important LLD principle

```text
Prefer:

Class
  ↓
Interface / Abstraction
  ↑
Multiple implementations

instead of:

Class
  ↓
Concrete implementation
```

This gives you **loose coupling + polymorphism + easier testing + extensibility**.
