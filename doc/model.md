# Bounded Contexts

## Gamebook

### Gamebook
- identifier
- title (MultilanguageText)
- author
- genres (Genre)
- nodes (Node)
- use cases
  - create book
  - delete book (only owned book)
  - update book details
  - export book 
  - add genre
  - remove genre
  
### Genre
- name (MultilanguageText)

### Node
- identifier
- content (MultilanguageText)
- options (Option)
- use cases
  - update content
  - add option
  - deattach option
  
### Option
- visibility precondition (Script)
- selectability precondition (Script)
- content (MultilanguageText)
- effect (Script)
- use cases
  - create option
  - delete option
  - attach to node
  - Unattach from node

### Script
- identifier
- script
- use cases
  - create script
  - delete script
  - update script 

### MultilanguageText

- texts per locale

## Online account

### User account
- user info (User)
- account type (AccountType)
- payment info (PaymentInfo)
- written books (GamebookInfo)
- owned books (Gamebook)
- use cases
  - Create user account
  - disable user account
  - create sephire.org.gamebook info
  - set default bank account
  - add book to owned list

### GamebookInfo
- book (Gamebook)
- enabled
- bank account (BankAccount)
- price (Price)
- use cases:
  - upload sephire.org.gamebook
  - export sephire.org.gamebook
  - disable sephire.org.gamebook
  - enable sephire.org.gamebook
  - set sephire.org.gamebook price
  - attach bank account
  - unattach bank account
  - delete sephire.org.gamebook info
  
### Price
- value
- currency

### AccountType
- enum
  - Basic
  - Premium
  
### User
- email
- name
- alias

### PaymentInfo
- banc accounts (BankAccount)
- credit cards (CreditCard)

### BankAccount
- identifier
- IBAN
- owner full name

### CreditCard
- identifier
- number
- owner full name