let { createApp } = Vue;

createApp({
    data() {
        return {
            creditCards: [],
            debitCards: [],
            currentDate: new Date(),
            cards: [],

        };
    },
    created() {
        this.loadData();
    },
    methods: {
        loadData() {
            axios.get("/api/clients/current/cards")
                .then((response) => {
                    this.creditCards = response.data.filter((card) => card.type == "CREDIT").sort((a, b) => a.id - b.id)
                    console.log(this.creditCards)
                    this.debitCards = response.data.filter((card) => card.type == "DEBIT").sort((a, b) => a.id - b.id)
                })
                .catch((err) => console.log(err));
        },
        signOut() {
            axios.post("/api/logout")
                .then((response) => {
                    location.href = "/index.html"
                }).catch((err) => console.log(err))
        },
        cardExpirationDate(date) {
            return new Date(date) < this.currentDate
        },
        // cardModify() {
        //     axios.get("/api/clients/current/cards")
        //         .then((response) => {
        //             this.cards = response.data
        //         }).catch((err) => console.log(err))
        // },

        changeCardColor(card) {
            let currentDate = new Date()
            if (card.color === "SILVER") {
                if (currentDate < new Date(card.thruDate)) {
                    return "silver-bg"
                } else return "expired-card-silver"
            } else if (card.color === "GOLD") {
                if (currentDate < new Date(card.thruDate)) {
                    return "gold-bg"
                } else return "expired-card"
            } else if (card.color === "TITANIUM") {
                if (currentDate < new Date(card.thruDate)) {
                    return "titanium-bg"
                } else return "expired-card"
            }
        },
        deleteCard(id) {
            console.log(id)
            axios.patch("/api/cards/modify", `active=${false}&id=${id}`)
                .then((response) => {
                    console.log(response.data)
                    this.loadData()
                }).catch((err) => console.log(err))
        }
    },
}).mount('#app');