let { createApp } = Vue;

createApp({
    data() {
        return {
            accounts: [],
            loans: [],
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
                    this.accounts = response.data.accounts.sort((a, b) => a.id - b.id)
                    this.loans = response.data.loans
                    console.log(response.data)
                    console.log(this.loans)
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