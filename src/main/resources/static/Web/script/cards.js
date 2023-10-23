let { createApp } = Vue;

createApp({
    data() {
        return {
            creditCards: [],
            debitCards: [],
        };
    },
    created() {
        this.loadData();
    },
    methods: {
        loadData() {
            axios
                .get("/api/current")
                .then((response) => {
                    this.creditCards = response.data.cards.filter((card) => card.type == "CREDIT").sort((a, b) => a.id - b.id)
                    console.log(this.cards)
                    this.debitCards = response.data.cards.filter((card) => card.type == "DEBIT").sort((a, b) => a.id - b.id)
                })
                .catch((err) => console.log(err));
        },
        signOut() {
            axios.post("/app/logout")
                .then((response) => {
                    console.log(response)
                    location.href = "http://localhost:8080/index.html"
                }).catch((err) => console.log(err))
        }
    },
}).mount('#app');