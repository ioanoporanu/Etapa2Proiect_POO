package playersout;

public final
class ContractOut {

    private
    long consumerId;
    private
    int price;
    private
    int remainedContractMonths;

    public
    long getConsumerId() {
        return consumerId; }

    public
    void setConsumerId(final long consumerId) {
        this.consumerId = consumerId; }

    public
    int getPrice() {
        return price; }

    public
    void setPrice(final int price) {
        this.price = price; }

    public
    int getRemainedContractMonths() {
        return remainedContractMonths; }

    public
    void setRemainedContractMonths(final int remainedContractMonths) {
        this.remainedContractMonths = remainedContractMonths;
    }
}
