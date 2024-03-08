namespace tuvarna_ecommerce_system.Exceptions
{
    public class CustomDuplicateKeyException : Exception
    {
        public CustomDuplicateKeyException(string message, Exception innerException)
            : base(message, innerException)
        {
        }
    }
}
