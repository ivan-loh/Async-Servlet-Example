# Sample Async vs Sync Servlet
This is a sample impementation on how async servlets can improve overall application performance introduce in Servlet 3.0. Included jmx file for JMeter for reproducing test.

## Motivation
Traditional application containers uses a Thread per connection method. Lengthy processing request can actually be moved to the background to allow for fast processing request to be processed using Async Servlets

## JMeter Test
With either the Sync or Async request disabled, run the test and see the performance difference in the Fast Request's Aggregate Graph