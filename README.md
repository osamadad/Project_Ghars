# 🌱 Ghars – Smart Agriculture Platform

## Overview

Ghars is a smart agriculture backend platform built using **Spring Boot**.  
The platform helps farmers manage farms, fields, crops, and yields, while providing AI-powered learning, plant identification, disease detection, virtual farming, events, and a full e-commerce system for agricultural products.

> **Core Idea:** Smart agriculture powered by AI, automation, and community engagement.

---

## 👥 Project Type

This project was developed as a **team-based backend project**, with responsibilities distributed among team members.

---

## 🎯 Project Objectives

- Digitize farm and crop management
- Improve agricultural productivity using AI insights
- Support learning-based agriculture for beginners and farmers
- Enable virtual farming and simulation
- Provide smart plant recommendations
- Offer an integrated store with payment and delivery
- Support community events and farmer engagement

---

## 🧱 System Architecture

| Layer | Description |
|------|------------|
| Controller | REST API endpoints |
| Service | Business logic & integrations |
| Repository | Database access (JPA) |
| Model | Entities |
| DTO | Input / Output objects |
| External Services | AI, Payment, Email, Automation |

---

## 🛠️ Technologies Used

- Java
- Spring Boot
- Spring Data JPA (Hibernate)
- Spring Security
- MySQL
- Lombok
- RESTful APIs
- AI integrations
- External services (PlantNet, Moyasar, Email, OpenAI)

---

## 🔐 Security

| Feature | Description |
|------|------------|
| Authentication | Spring Security |
| Authorization | Role-based (USER / ADMIN) |
| Access Control | Configured using `requestMatchers` |
| Testing | Postman API testing |
| Testing | Junit testing |

Security configuration contributed by **Ibrahim** with support from **Fouz**.

---

## 👥 Team Contributions

### 👨‍💻 Osama

**Entities**
- Farm
- Address
- Field
- PlantType
- Yield
- Virtual Farm
- Virtual Plot

**External Integrations**
- PlantNet API
- Moyasar Payment Gateway (shared)
- OpenAI (shared)

**Work Summary**
- Implemented core agriculture entities and business logic
- Built advanced plant filtering system
- Implemented AI-based learning and discovery features
- Integrated PlantNet for plant identification
- Contributed to payment integration (Moyasar)
- Implemented farmer analytics and ranking features
- Implemented the virtual farm automation game through n8n
- Tested the system using Junit test

---

### 👨‍💻 Ibrahim

**Entities**
- User
- Farmer
- Customer
- Achievement
- Virtual Farm
- Virtual Plot
- Review
- Event
- Address

**External Integrations**
- Email Service (SMTP / Gmail)
- OpenAI

**Work Summary**
- Implemented core user and farmer management
- Built achievement and progress tracking system
- Implemented learning-based agriculture endpoints
- Managed license upload and approval workflow
- Handled events and event information
- Implemented virtual farming logic
- Implemented email notification system
- Implemented security configuration (`requestMatchers`)
- Postman API testing (with Fouz)

---

### 👨‍💻 Fouz

**Entities (All CRUD)**
- Stock
- Product
- Order
- OrderItem
- Invoice
- Delivery
- Payment

**External Integrations**
- Moyasar Payment Gateway (shared)
- OpenAI (shared)

**Work Summary**
- Implemented product and stock management
- Built full order lifecycle and order item handling
- Implemented invoice and payment logic
- Integrated Moyasar payment gateway
- Managed delivery process and driver assignment
- Handled order tracking, refunds, and confirmations

---

## 🤖 AI & Smart Features

- AI-powered plant learning
- Plant identification using images
- Plant disease detection using images
- Smart plant recommendations
- Seasonal and location-based suggestions
- adding plants using AI

---

## 🌐 External Services

| Service | Purpose |
|------|--------|
| PlantNet API | Plant identification |
| OpenAI | Learning & recommendations (shared) |
| Moyasar | Payments (shared) |
| N8N | Virtual farm automation |
| Email (SMTP / Gmail) | Notifications |
| Whatsapp | Communication between users |


---

## 📦 Deliverables

| Deliverables | Link |
|------|--------|
| Presentation | [Presentation](https://drive.google.com/file/d/1r1YDjbyCwtb1u1vng-a0EZaGvKuzg6w2/view?usp=drive_link) |
| Figma | [Figam](https://www.figma.com/design/0GnFPaEWL0pNQ6WeAUonFx/%D8%BA%D8%B1%D8%B3?node-id=0-1&t=zhbTtny5ZUantdoX-1) |
| Class diagram | [Class diagram](https://drive.google.com/file/d/1bazr4Rj8MIqXq4VRFbGhFs__ZGQzoJOU/view?usp=drive_link) |
| Use case diagram | [Use case diagram](https://drive.google.com/file/d/1Z15M3bTLHXQe6vrUnr7irsdpVXH9-VP7/view?usp=drive_link) |
| Postman API Testing | [Postman collection](https://documenter.getpostman.com/view/45708429/2sBXVbJZc1) |
| Domain | [Ghars](http://ghars.eu-central-1.elasticbeanstalk.com/) |

---

## 🚀 Future Enhancements

- Advanced smart irrigation scheduling
- More AI-driven recommendations
- Frontend integration
- Mobile application support

---

## Detailed API Endpoints

### AddressController (`/api/v1/address`)

| Method | Path | Description | Author |
|--------|------|-------------|--------|
| POST | `/add` | Create address for the authenticated user | Ibrahim |
| GET | `/get` | Get all addresses | Ibrahim |
| GET | `/get-my-address` | Get my address | Ibrahim |
| PUT | `/update` | Update my address | Ibrahim |
| DELETE | `/delete` | Delete my address | Ibrahim |

---

### CustomerController (`/api/v1/customer`)

| Method | Path | Description | Author |
|--------|------|-------------|--------|
| POST | `/register` | Register customer profile | Ibrahim |
| GET | `/get` | Get my customer profile | Ibrahim |
| PUT | `/update` | Update my customer profile | Ibrahim |
| DELETE | `/delete` | Delete my customer profile | Ibrahim |

---

### FarmerController (`/api/v1/farmer`)

| Method | Path | Description | Author |
|--------|------|-------------|--------|
| POST | `/register` | Register farmer profile | Ibrahim |
| GET | `/get` | Get all farmer profile | Ibrahim |
| GET | `/get-my-info` | Get all farmer profile | Ibrahim |
| PUT | `/update` | Update my farmer profile | Ibrahim |
| POST | `/talk-about-plant/{farmerId}/{plantName}` | Talk with other farmer who planted this plant | Osama |
| POST | `/talk/{farmerId}/{message}` | Talk with other farmers | Osama |
| GET | `/by-city` | Get farmers in this city | Osama |
| GET | `/by-level` | Get farmers in this level range | Osama |
| GET | `/by-rank` | Get farmers in this rank | Osama |
| GET | `/most-level` | Get farmer with the highest level | Osama |
| GET | `/most-yield` | Get farmer with the most yield (score) | Osama |
| GET | `/most-seasonal-yield` | Get farmer with the most seasonal yield (score) | Osama |
| PUT | `/reset-seasonal-yield` | Reset the seasonal yield for all farmers | Osama |





---

### DriverController (`/api/v1/driver`)

| Method | Path | Description | Author |
|--------|------|-------------|--------|
| POST | `/register` | Register driver profile | Fouz |
| GET | `/get` | Get my driver profile | Fouz |
| PUT | `/update` | Update my driver profile | Fouz |
| DELETE | `/delete` | Delete my driver profile | Fouz |

---

### DeliveryController (`/api/v1/delivery`)

| Method | Path | Description | Author |
|--------|------|-------------|--------|
| GET | `/my` | Get my deliveries | Fouz |
| POST | `/create/{orderId}/{driverId}` | Create delivery for an order and assign driver | Fouz |
| PUT | `/update-status/{deliveryId}?status=...` | Update delivery status | Fouz |

---

### EventController (`/api/v1/event`)

| Method | Path | Description | Author |
|--------|------|-------------|--------|
| POST | `/add` | Add new event | Ibrahim |
| GET | `/get` | Get all events | Ibrahim |
| GET | `/get/{eventId}` | Get event by ID | Ibrahim |
| PUT | `/update/{eventId}` | Update event | Ibrahim |
| DELETE | `/delete/{eventId}` | Delete event | Ibrahim |
| POST | `/join/{eventId}` | Join event | Ibrahim |
| POST | `/leave/{eventId}` | Leave event | Ibrahim |
| GET | `/upcoming` | Get upcoming events | Ibrahim |
| GET | `/upcoming/city/{city}` | Get upcoming events by city | Ibrahim |

---

### FarmController (`/api/v1/farm`)

| Method | Path | Description | Author |
|--------|------|-------------|--------|
| POST | `/add` | Add farm | Osama |
| GET | `/get` | Get all farms | Osama |
| GET | `/get-my-farm` | Get my farm | Osama |
| PUT | `/update/{farmId}` | Update farm | Osama |
| DELETE | `/delete/{farmId}` | Delete farm | Osama |
| PUT | `/add-license/{farmId}` | Upload/add farm license (pending approval) | Ibrahim |
| PUT | `/license/accept/{farmId}` | Accept farm license | Ibrahim |
| PUT | `/license/reject/{farmId}` | Reject farm license | Ibrahim |

---

### FieldController (`/api/v1/field`)

| Method | Path | Description | Author |
|--------|------|-------------|--------|
| POST | `/add` | Add field | Osama |
| GET | `/get` | Get all fields | Osama |
| GET | `/get-by-farm/{farmId}` | Get my fields by farm | Osama |
| PUT | `/update/{fieldId}` | Update field | Osama |
| DELETE | `/delete/{fieldId}` | Delete field | Osama |

---

### YieldController (`/api/v1/yield`)

| Method | Path | Description | Author |
|--------|------|-------------|--------|
| POST | `/add` | Add yield | Osama |
| GET | `/get` | Get all yields | Osama |
| GET | `/get-by-field/{fieldId}` | Get my yields by field | Osama |
| PUT | `/update/{yieldId}` | Update yield | Osama |
| DELETE | `/delete/{yieldId}` | Delete yield | Osama |

---

### PlantTypeController (`/api/v1/plant`)

| Method | Path | Description | Author |
|--------|------|-------------|--------|
| POST | `/add` | Add plant type | Osama |
| GET | `/get` | Get all plant types | Osama |
| PUT | `/update/{plantTypeId}` | Update plant type | Osama |
| DELETE | `/delete/{plantTypeId}` | Delete plant type | Osama |
| GET | `/family/{family}` | Filter plants by family | Osama |
| GET | `/category/{category}` | Filter plants by category | Osama |
| GET | `/size/{size}` | Filter plants by size | Osama |
| GET | `/growth-speed/{growthSpeed}` | Filter plants by growth speed | Osama |
| GET | `/water-needs/{waterNeeds}` | Filter plants by water needs | Osama |
| GET | `/sun-needs/{sunNeeds}` | Filter plants by sun needs | Osama |
| GET | `/season/{season}` | Filter plants by season | Osama |
| GET | `/difficulty/{difficultyLevel}` | Filter plants by difficulty | Osama |
| GET | `/growing-medium/{growingMedium}` | Filter plants by growing medium | Osama |
| GET | `/planting-place/{plantingPlace}` | Filter plants by planting place | Osama |

---

### AIController (`/api/v1/ai`)

| Method | Path | Description | Author |
|--------|------|-------------|--------|
| GET | `/soil-seeds` | AI lesson: Soil & Seeds | Ibrahim |
| GET | `/home-gardening` | AI lesson: Home Gardening | Ibrahim |
| GET | `/watering-fertilizing` | AI lesson: Watering & Fertilizing | Ibrahim |
| GET | `/plant-care` | AI lesson: Plant Care | Ibrahim |
| GET | `/plant-problems` | AI lesson: Plant Problems | Ibrahim |
| GET | `/recommend-event` | Recommend best event | Ibrahim |
| POST | `/identify/{organ}` | PlantNet: identify plant (image) | Osama |
| POST | `/identify-diseases/{organ}` | PlantNet: identify disease (image) | Osama |
| GET | `/learn/green-house` | AI learning: greenhouse | Osama |
| GET | `/learn/water-planting` | AI learning: water planting | Osama |

---

### VirtualFarmController (`/api/v1/virtualfarm`)

| Method | Path | Description | Author |
|--------|------|-------------|--------|
| POST | `/add` | Add virtual farm | Ibrahim |
| GET | `/get` | Get my virtual farms | Ibrahim |
| GET | `/get/{virtualFarmId}` | Get my virtual farm by ID | Ibrahim |
| PUT | `/update/{virtualFarmId}` | Update virtual farm | Ibrahim |
| DELETE | `/delete/{virtualFarmId}` | Delete virtual farm | Ibrahim |

---

### VirtualPlotController (`/api/v1/virtual-plot`)

| Method | Path | Description | Author |
|--------|------|-------------|--------|
| POST | `/add/{virtualFarmId}/{plotType}` | Add virtual plot | Ibrahim |
| GET | `/get/{virtualFarmId}` | Get my plots by virtual farm | Ibrahim |
| GET | `/get-by-id/{virtualPlotId}` | Get my plot by ID | Ibrahim |
| DELETE | `/delete/{virtualPlotId}` | Delete virtual plot | Ibrahim |
| PUT | `/assign-plant/{plotId}/{plantId}` | Assign plant to plot | Ibrahim |
| PUT | `/uproot/{plotId}` | Uproot plant from plot | Ibrahim |
| PUT | `/add-water/{plotId}` | Add water | Osama |
| PUT | `/add-sun/{plotId}` | Add sun | Osama |
| PUT | `/decrease-water/{plotId}` | Decrease water | Osama |
| PUT | `/decrease-sun/{plotId}` | Decrease sun | Osama |
| PUT | `/check-plant/{plotId}` | Check plant status | Osama |
| PUT | `/harvest/{plotId}` | Harvest plant | Osama |

---

### ReviewController (`/api/v1/review`)

| Method | Path | Description | Author |
|--------|------|-------------|--------|
| POST | `/add/{farmId}` | Add review to farm | Ibrahim |
| GET | `/get` | Get all reviews | Ibrahim |
| GET | `/get-by-farm/{farmId}` | Get reviews by farm | Ibrahim |
| PUT | `/update/{reviewId}` | Update review | Ibrahim |
| DELETE | `/delete/{reviewId}` | Delete review | Ibrahim |

---

### ProductController (`/api/v1/product`)

| Method | Path | Description | Author |
|--------|------|-------------|--------|
| GET | `/get-all` | Get all products | Fouz |
| GET | `/my-products` | Get my products | Fouz |
| POST | `/add` | Add product | Fouz |
| PUT | `/update/{productId}` | Update product | Fouz |
| DELETE | `/delete/{productId}` | Delete product | Fouz |
| GET | `/by-sell-type/{sellType}` | Filter store by sell type | Osama |
| GET | `/order-by-price` | Get products ordered by price | Osama |
| GET | `/price-range/{minPrice}/{maxPrice}` | Filter products by price range | Osama |

---

### StockController (`/api/v1/stock`)

| Method | Path | Description | Author |
|--------|------|-------------|--------|
| GET | `/get-all` | Get all stocks | Fouz |
| GET | `/my-stock` | Get my stock | Fouz |
| PUT | `/add-quantity/{stockId}` | Add stock quantity | Fouz |
| PUT | `/reduce-quantity/{stockId}` | Reduce stock quantity | Fouz |
| PUT | `/update/{stockId}` | Update stock | Fouz |
| DELETE | `/delete/{stockId}` | Delete stock | Fouz |

---

### OrderController (`/api/v1/order`)

| Method | Path | Description | Author |
|--------|------|-------------|--------|
| GET | `/get-all` | Get all orders | Fouz |
| GET | `/my-orders` | Get my orders | Fouz |
| POST | `/create` | Create order | Fouz |
| POST | `/pay/{orderId}` | Pay order | Fouz |
| POST | `/return/{orderId}` | Request return | Fouz |
| POST | `/confirm-return/{orderId}` | Confirm return & refund | Fouz |
| PUT | `/update-status/{orderId}?status=...` | Update order status | Fouz |
| DELETE | `/delete/{orderId}` | Delete order | Fouz |

---

### OrderItemController (`/api/v1/order-item`)

| Method | Path | Description | Author |
|--------|------|-------------|--------|
| GET | `/get/{orderId}` | Get order items by order | Fouz |
| POST | `/add/{orderId}` | Add item to order | Fouz |
| PUT | `/update/{orderItemId}` | Update order item | Fouz |
| DELETE | `/delete/{orderItemId}` | Delete order item | Fouz |

---

### InvoiceController (`/api/v1/invoice`)

| Method | Path | Description | Author |
|--------|------|-------------|--------|
| GET | `/get-all` | Get all invoices | Fouz |
| GET | `/my-invoices` | Get my invoices | Fouz |
| POST | `/create/{orderId}` | Create invoice for order | Fouz |
| PUT | `/update/{invoiceId}` | Update invoice | Fouz |
| DELETE | `/delete/{invoiceId}` | Delete invoice | Fouz |
| GET | `/platform-profit` | Get platform total profit | Fouz |

---

### PaymentController (`/api/v1/payment`)

| Method | Path | Description | Author |
|--------|------|-------------|--------|
| POST | `/pay/{orderId}` | Start payment (Moyasar gateway) | Fouz and Osama |

---

### LevelController (`/api/v1/level`)

| Method | Path | Description | Author |
|--------|------|-------------|--------|
| POST | `/add` | Add level | Ibrahim |
| GET | `/get` | Get all levels | Ibrahim |
| PUT | `/update/{levelId}` | Update level | Ibrahim |
| DELETE | `/delete/{levelId}` | Delete level | Ibrahim |

---

### AchievementController (`/api/v1/achievement`)

| Method | Path | Description | Author |
|--------|------|-------------|--------|
| POST | `/add` | Add achievement | Ibrahim |
| GET | `/get` | Get all achievements | Ibrahim |
| PUT | `/update/{achievementId}` | Update achievement | Ibrahim |
| DELETE | `/delete/{achievementId}` | Delete achievement | Ibrahim |

---

### FarmerAchievementController (`/api/v1/farmer-achievement`)

| Method | Path | Description | Author |
|--------|------|-------------|--------|
| POST | `/unlock/{achievementId}` | Unlock achievement | Ibrahim |
| GET | `/my` | Get my achievements | Ibrahim |

---

### UserController (`/api/v1/user`)

| Method | Path | Description | Author |
|--------|------|-------------|--------|
| POST | `/register` | Register admin user | Ibrahim |
