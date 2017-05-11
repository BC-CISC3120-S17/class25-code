# class25-code
## WebSocketsScramble
We talked about this a bit in class; here's the code for your study. (Don't waste a lot of time worrying about the game logic; that's not the point. Focus on the WebSockets interactions.)

## WebSocketsPoke
This application makes more complex use of WebSockets than the word-scramble game. (Note that the design of the client is pretty rough&mdash;it would probably be better to separate the GUI stuff and the WebSockets into different classes, but I wanted it to be easier for you to see those parts working together.) 

### Analysis

When is the GUI actually displayed? What happens between when a button is clicked and when a messages shows up in the display? What is a `Message`? How are `Message` objects sent between the client and server?


### Add another Button
OK, so, add another button to the client interface called *Pickle*. This should work just like the *Poke* and *Prod* buttons, except that the message should be something like “... pickled.” What changes have to be made?
