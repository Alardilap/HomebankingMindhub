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

            axios.get(`/api/accounts/${id}`)
                .then((response) => {
                    this.account = response.data
                    this.transactions = response.data.transactions.sort((a, b) => b.id - a.id)
                    console.log(transactions)
                })
                .catch((err) => console.log(err));
        },
        loadDate(date) {
            return moment(date).format('lll');
        }

    },
}).mount('#app');