# Sample Async vs Sync Servlet
This is a sample impementation on how async servlets can improve overall application performance introduce in Servlet 3.0. Included jmx file for JMeter for reproducing test.

## Motivation
Traditional application containers uses a Thread per connection method, long processing request can unnessarily hold up a thread for no reason. These lengthy processing request can actually be moved to the background using Async Servlets to allow for fast processing request to be processed.

## JMeter Test
With either the Sync or Async request disabled, run the test and see the performance difference in the Fast Request's Aggregate Graph