In this folder we have a global exception handler that will catch 3 specific exceptions

* AccessDeniedException
* AuthenticationException
* ResponseStatusException

And for the rest of exception we will get internal server error, all of this exception will follow the same format ( ErrorResponse from Spring Framework Web )