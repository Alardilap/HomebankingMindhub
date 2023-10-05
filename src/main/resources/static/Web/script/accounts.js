let { createApp } = Vue;

createApp({
    data() {
        return {
            accounts: [],
            clients: [],
        };
    },
    created() {
        this.loadData();
    },
    methods: {
        loadData() {
            axios
                .get("/api/clients/3")
                .then((response) => {
                    // console.log(response.data)
                    this.accounts = response.data.accounts
                    this.clients = response.data
                    console.log(this.clients)
                })
                .catch((err) => console.log(err));
        },

    },
}).mount('#app');