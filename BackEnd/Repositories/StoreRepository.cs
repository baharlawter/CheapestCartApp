public interface IStoreRepository
{
    Task <IEnumerable<StoreModel>> GetAllAsync();
    Task <StoreModel?> GetByIdAsync (ulong id);
    Task AddAsync (StoreModel storeModel);
}
