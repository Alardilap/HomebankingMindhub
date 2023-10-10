let { createApp } = Vue;

createApp({
    data() {
        return {
            accounts: [],
        };
    },
    created() {
        this.loadData();


    },
    methods: {
        loadData() {
            axios
                .get("/api/accounts")
                .then((response) => {
                    // console.log(response.data)
                    this.accounts = response.data.sort((a, b) => a.id - b.id)
                    console.log(this.accounts)

                })
                .catch((err) => console.log(err));

        },
        obtenerId() {


        }

    },
}).mount('#app');