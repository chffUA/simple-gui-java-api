# Simple execution Java API with logging GUI

To run from command line: `mvn spring-boot:run`

To clean: `mvn clean`

To compile into JAR: `mvn package` (Eclipse will NOT package the JAR properly, only Maven will)

## What purpose does it serve?

- Example of how to use spring framework to make a REST API deployable through a single JAR file

- Logging capabilities (and multiple message types / log modes)

- Example of how to include and package resources (image used by UI class)

- Includes API integration tests (which are also automatically run by `mvn package`)

- Java Swing user interface using dialogs, textboxes and buttons

-----

Preview:

![](https://i.imgur.com/iE8YdDE.png)

![](https://i.imgur.com/ZewdQEL.png)

![](https://i.imgur.com/Mo4g6Ov.png)

-----

Base path: **http://localhost:8080/**

## /user (adds new user)

**Type:** POST

**Requires:** JSON string

**Fields:**

- name - `string`

**Returns:** JSON string

**Error fields:**

- error - `string`
	
**Success fields:**
	
- id - `integer`

## /item (adds new item)

**Type:** POST

**Requires:** JSON string

**Fields:**

- name - `string`
	
- price - `double`

**Returns:** JSON string

**Error fields:**

- error - `string`
	
**Success fields:**
	
- id - `integer`
	
## /user/{uid}

**Type:** GET

**Requires:** uid (can be integer or string)

**Returns:** JSON string

**Error fields:**

- error - `string`
	
**Success fields:**

- uid - `integer`

- name - `string`

## /item/{uid}

**Type:** GET

**Requires:** uid (can be integer or string)

**Returns:** JSON string

**Error fields:**

- error - `string`
	
**Success fields:**

- id - `integer`

- name - `string`

- price - `double`
