namespace tuvarna_ecommerce_system.Exceptions
{
    public class InternalServerErrorException : Exception
    {
        public InternalServerErrorException(string message, Exception innerException)
            : base(message, innerException)
        {
        }
    }
}
