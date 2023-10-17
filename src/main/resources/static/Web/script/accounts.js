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
                .get("/api/clients/2")
                .then((response) => {
                    // console.log(response.data)
                    // this.accounts = response.data.sort((a, b) => a.id - b.id)
                    this.accounts = response.data.accounts.sort((a, b) => a.id - b.id)
                    this.loans = response.data.loans
                    console.log(this.loans)

                })
                .catch((err) => console.log(err));
        },
    },
}).mount('#app');