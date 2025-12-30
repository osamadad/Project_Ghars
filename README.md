🌱 Ghars – Smart Agriculture Platform

Ghars is a smart agriculture backend platform built using Spring Boot.
The platform helps farmers manage farms, fields, crops, and yields, while providing AI-powered learning, plant identification, disease detection, virtual farming, events, and a full e-commerce system for agricultural products.

🎯 Project Objectives

Digitize farm and crop management

Improve agricultural productivity using AI insights

Support learning-based agriculture for beginners and farmers

Enable virtual farming and simulation

Provide smart plant recommendations

Offer an integrated store with payment and delivery

Support community events and farmer engagement

🧱 System Architecture

The project follows a layered architecture:

Controller Layer – RESTful APIs

Service Layer – Business logic and integrations

Repository Layer – Database access using JPA

Model Layer – Entities

DTO Layer – Data transfer objects

External Services – AI, Payment, Email, Automation

🛠️ Technologies Used

Java

Spring Boot

Spring Data JPA (Hibernate)

Spring Security

MySQL

Lombok

RESTful APIs

AI integrations

External services (PlantNet, Moyasar, Email)

👥 Team Contributions
Osama

Entities
Farm
Address
Field
PlantType
Yield
Virtual Farm
Virtual Plot

Endpoints
Plant by family
Plant by category
Plant by size
Plant by growth speed
Plant by water needs
Plant by sun needs
Plant by season
Plant by difficulty
Plant by growing medium
Plant by planting place
Add water
Add sun
Harvest plant
AI learning about greenhouse
AI learning about water plantation
AI plant discovery
Upload image for plant identification
Upload image for disease identification
Speak with users
Speak with a farmer who planted this plant
Farmer with the most yield
Farmer with the most seasonal yield
Farmer with the highest level
Farmer by rank
Farmer by level range
Farmer by city
Farmer by experience
Farmer by level
Reset seasonal yield
Get farmers who planted a specific plant

External Integrations
PlantNet API
Moyasar Payment Gateway (shared)

Work Summary
Implemented core agriculture entities and business logic
Built advanced plant filtering system
Implemented AI-based learning and discovery features
Integrated PlantNet for plant identification
Contributed to payment integration (Moyasar)
Implemented farmer analytics and ranking features

Ibrahim

Entities
User
Farmer
Customer
Achievement
Virtual Farm
Virtual Plot
Review
Event
Address

Endpoints
Upload license
Accept license
Reject license
Learn agriculture
Soil and seeds learning
Home agriculture learning
Watering and fertilizing learning
Plant care learning
Check event information
Join event
Leave event
Upcoming events
Events by city
Achievement unlocking and progress tracking
Farmer profile management
Virtual farm and virtual plot core logic

External Integrations
Email Service (SMTP / Gmail)

Work Summary
Implemented core user and farmer management
Built achievement and progress tracking system
Implemented learning-based agriculture endpoints
Managed license upload and approval workflow
Handled events and event information
Implemented virtual farming logic
Implemented email notification system

Fouz

Entities
Stock
Product
Order
OrderItem
Invoice
Delivery
Payment

Endpoints
Check order status
Refund order
Confirm return
Update order status
Change product price
Add stock
Reduce stock
Assign driver to order
Delivery status management
Event registration
Send event notification email
Store filter by sell type
Store filter by price range
Store filter by price order
Seasonal and suitable plants
Filter plants by location
Recommend best plant for user
Payment processing and checkout

External Integrations
Moyasar Payment Gateway (shared)

Work Summary
Implemented product and stock management
Built full order lifecycle and order item handling
Implemented invoice and payment logic
Integrated Moyasar payment gateway
Managed delivery process and driver assignment
Handled order tracking, refunds, and confirmations

🤖 AI & Smart Features

AI-powered plant learning

Plant identification using images

Plant disease detection

Smart plant recommendations

Seasonal and location-based suggestions

🌐 External Services

PlantNet API

Moyasar Payment Gateway

Email (SMTP / Gmail)

🚀 Future Enhancements

Advanced smart irrigation scheduling

More AI-driven recommendations

Frontend integration

Mobile application support
