# README

## Specification considerations/clarifications

### Viability of React and Django
Source: https://edstem.org/au/courses/7600/discussion/774553

Q: Concerns on using frameworks (e.g. React front-end and Django back-end, OR Django and Django HTML templates) - particularly, in reference to showing design principles and ease of use in applying them

A: Applying design patterns and principles are easy with pure Object Oriented Programming languages (OOPL) because we are using Object Oriented Principles that uses OO pillars and principles (OCP etc).

If you want to choose non-pure OOPL (i.e. functional langs), you need to demonstrate how the chosen language can adhere to some of the design patterns. For example, in some design patterns, we will need to implement an interface and extend an abstract class at the same time. Your chosen language must support it.

### Is registration needed?
Source: https://edstem.org/au/courses/7600/discussion/776820

A: Students are not expected to develop a user registration page/module. You can use the existing pre-populated users. 

However, if the current pre-populated users is not able to "showcase" all the features that you have implemented, you can choose to add new users/modify the existing users.

### OOP Languages/frameworks
Use any OOPL for backend, and any language for frontend. OOPLs include:
- Java and related frameworks (such as Spring Boot)
- Python (OO Principles applicable)
### Non-OOPLs
- PHP (*Object-capable* but **not** OOPL)

### Testing site properties
Source: https://edstem.org/au/courses/7600/discussion/786784

Q1: Can a facility not provide testing? Does "testing" refer to "types of testing" or ability to provide testing services?

A1: The term "testing" refers to ability to provide testing services.

Q2: Assumption of business hours and waiting times: Since JSON schema of a testing site does not contain fields for business hours and waiting time, does this mean that we are safe to make assumptions on these two properties?

A2: Make use of additionalInfo field.

### Check booking status using PIN code
Source: https://edstem.org/au/courses/7600/discussion/786793

Q: Is status checking also part of on-site booking subsystem? If yes, is displaying the "string" inside the "status" field enough to satisfy the requirement of this feature?

A: The function is required; As a user/resident, the PIN that they received can be passed to the on-site facility staff to check the status of their booking (assuming that the resident does not have direct access to the online system).

### QR Code & URL for Home Testing
Source: https://edstem.org/au/courses/7600/discussion/786823

I have a few questions regarding the 5th subsystem - home booking (book for home testing)

Q1. Dummy QR code & URL: Since the endpoint is not providing any QR code and URL, does this mean that we need to generate a dummy QR code & URL by ourselves?

A1: Yes - use additionalInfo field.

Q2: Will the system always generate a QR code regardless of whether the user has already bought a kit or not?

A2: Yes.

Q3: The system automatically emails and texts the URL, but will the QR code be sent via email and text as well? 

A3: Yes, but you don't need to implement real texting and emailing service. However, you may need to show somehow that the text is sent etc.


### Search for Testing Sites [User - Users/Residents]
Source: https://edstem.org/au/courses/7600/discussion/787097

Q: Can users choose to display testing sites in map/tabular form? If map is required, do we need to use external Map API according to our preference?

A: You can choose to display testing sites in tabular (list) or map form.

### Home Booking Subsystem
Source: https://edstem.org/au/courses/7600/discussion/787097

Q: Once the booking is confirmed, the user will be given a QR code and a URL to connect for testing. What is the use of URL?

A: URL is used to connect with experts to perform supervised testing, which is for groups of 3 in assignment 2. You can use any dummy URL for this purpose. Hint: functional completeness is always necessary.

### Pin Code & QR Code
Source: https://edstem.org/au/courses/7600/discussion/787097

Q1: PIN Code is used by users/residents to check status of their booking. What does the status here mean? 

Q2: Once the booking is done, a PIN code will be generated for each unique booking. Doesn't it mean that when the booking is placed successfully, then the user will have the PIN code generated? Then, is the PIN code still required after all?

A1/2: Users can pass the PIN code to the on-site staff (reception) to check their booking. You can display the status of the booking (i.e. time, place, etc). 

Q3: Facility staff scans the QR code from the users and verify user identity. It seems that to verify whether the user has made a booking, the QR code needs be scanned by the facility staff to check for testing validity. Then, Facility staff updates the system to inform that the users have received the RAT kit from the facility. Does updating the system here refer to updating status property of Booking class OR CovidTest class?

A3: Depends on where you store the information about whether the user has received the RAT kit - use additionalInfo field of the Booking object.

### "result" property of CovidTest class
Source: https://edstem.org/au/courses/7600/discussion/787097

Q: Groups of 2 do not have to implement Video conferencing subsystem. Could I safely assume that we do not need to modify the result property of CovidTest class?

A: Result property here refers to the test result - positive, negative or invalid. You still need to implement it because of the functional completeness and correctness.

### "status" property of CovidTest and Booking class
Source: https://edstem.org/au/courses/7600/discussion/787097

Q: Based on my understanding towards API, status property might have a special meaning such as a statusCode of "1" or "0" etc. Referring to the example JSON object, 

    status property of CovidTest could be "CREATED"

    status property of Booking could be "INITIATED"

Therefore, could I safely assume that status property of CovidTest and Booking class can be freely manipulated by us such as changing to COMPLETED value?

A: Yes, you can store any String value in the "status" field. 

### QR code generation
Source: https://edstem.org/au/courses/7600/discussion/787097

Booking at the facility: If the facility has a system to generate QR codes and register users, the user will be asked to provide a phone number and the PIN code will be sent as a text message. **The user will also scan a QR code generated by the staff at the facility.**

I thought that the booking ends after the user scans a QR code. However in A2, it is only stated that the user will receive a PIN code when a booking is made (which means that the booking process has finished). Does this mean that the staff still needs to generate the QR from the user's PIN for the booking to be concluded?

A: Not needed; we diluted the assignment spec a bit for A2, so that students can focus on some of the more important feature of the system. Hence, you will just need to implement features mentioned in A2.

### Search testing site without logging in
Source: https://edstem.org/au/courses/7600/discussion/788266

A: Yes, users can search testing sites without logging in.

### Assignment 2 Scope
Source: https://edstem.org/au/courses/7600/discussion/788362

Q: Should we include MVC (Model-View-Controller) architecture [note: Wk07 content] in our class diagram? Or just implement design principles and patterns from previous weeks?

A: No; architectures are out of scope. You should not use such architectures. Even if you apply MVC in A2, you may have to rewrite the code for A3, since different architectures are covered.

### Healthcare workers and on-site bookings
Source: https://edstem.org/au/courses/7600/discussion/790601

Q: Can the health care workers make a booking or are they only updating the booking from task 4 (on-site testing)? It seems like it would be more logical to assume that they can make a booking, however, they are not explicitly stated to be able to do so. 

A: No, healthcare workers do not make bookings on the system; users/residents and receptionists (facility staff) do.