package players;

public final
class Contract {

    private
    Consumer consumer;

    public
    Consumer getConsumer() {
        return consumer; }

    public
    void setConsumer(final Consumer consumer) {
        this.consumer = consumer; }

    public
    Distributor getDistributor() {
        return distributor; }

    public
    void setDistributor(final Distributor distributor) {
        this.distributor = distributor;
    }

    public
    int getPrice() {
        return price; }

    public
    void setPrice(final int price) {
        this.price = price; }

    public
    int getValability() {
        return valability; }

    public
    void setValability(final int valability) {
        this.valability = valability; }

    private
    Distributor distributor;
    private
    int price;
    private
    int valability;
}
