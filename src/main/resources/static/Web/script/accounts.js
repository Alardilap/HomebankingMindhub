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
            axios.post("/api/logout")
                .then((response) => {
                    console.log(response.data)
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        iconColor: 'grey',
                        title: 'logout ok',
                        showConfirmButton: false,
                        timer: 1500
                    })
                    setTimeout(() => {
                        location.href = "/index.html"
                    }, 1700)


                }).catch((err) => console.log(err))
        },
        createAccount() {
            axios.post("/api/clients/current/accounts")
                .then((response) => {
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        iconColor: 'green',
                        title: 'account created successfully',
                        showConfirmButton: false,
                        timer: 1500
                    })
                    setTimeout(() => {
                        this.loadData()
                    }, 1700)

                }).catch((err) => {
                    let errorMessage = err.response.data;
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        iconColor: 'red',
                        text: errorMessage,
                        showConfirmButton: false,
                        timer: 1500,
                        customClass: {
                            text: 'custom-swal-text' // Definir una clase personalizada
                        }
                    })
                }

                )
        }
    },
}).mount('#app');