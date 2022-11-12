# Shortner Project

Use this application in order to easely provide redirections, for other applications.
A bit like bit.ly, etc, ...

Nothing fancy, just to easely be used.

The same url will be mapped to the same key as long as the store are not detsroyed and cycled.

Two stores are available for now:

- in-memory
- redis

can be set using the env variable: STORE_TYPE

if using then set REDIS_HOST to the address of the redis database
