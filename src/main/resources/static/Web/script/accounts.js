let { createApp } = Vue;

createApp({
    data() {
        return {
            accounts: [],
            loans: [],
            accountTypes: [],
            selectaccountType: "",
        };
    },
    created() {
        this.loadData();
        this.accountType();
    },
    methods: {
        loadData() {
            axios.get("/api/current")
                .then((response) => {
                    this.accounts = response.data.accounts.sort((a, b) => a.id - b.id)
                    this.loans = response.data.loans
                })
                .catch((err) => console.log(err));
        },
        accountType() {
            axios.get("/api/accountType")
                .then((response) => {
                    this.accountTypes = response.data
                }).catch((err) => console.log(err));
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
        deleteAccount(numberAccount) {
            axios.patch("/api/account/modify", `number=${numberAccount}`)
                .then((response) => {
                    this.loadData()
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        iconColor: 'grey',
                        title: 'Deleted account',
                        showConfirmButton: false,
                        timer: 1500
                    })
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
                            text: 'custom-swal-text'
                        }
                    })
                })
        },

        metododos() {
            axios.post("/api/clients/current/accounts", `accountType=${this.selectaccountType}`)
                .then((response) => {
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        iconColor: 'green',
                        title: 'account created successfully',
                        showConfirmButton: false,
                        timer: 1500
                    })
                    console.log(response.data)
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
                            text: 'custom-swal-text'
                        }
                    })
                })
        },


    }
}).mount('#app');