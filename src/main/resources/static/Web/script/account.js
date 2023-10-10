let { createApp } = Vue;

createApp({
    data() {
        return {
            account: {},
            transactions: [],
        };
    },
    created() {
        this.loadAccount();

    },
    methods: {
        loadAccount() {
            let queryParams = new URLSearchParams(window.location.search);
            let id = queryParams.get("id")
            console.log(id)
            axios
                .get(`/api/account/${id}`)
                .then((response) => {
                    this.account = response.data
                    this.transactions = response.data.transactions
                    console.log(transactions)
                })
                .catch((err) => console.log(err));
        },


    },
}).mount('#app');