let { createApp } = Vue;

createApp({
    data() {
        return {
            accountPayment: "",
            queryId: "",
            loan: {},
            idLoan: null,
            accounts: [],
        };
    },
    created() {
        this.detailsLoan()
        this.getaccounts()
    },
    methods: {
        detailsLoan() {
            this.queryId = new URLSearchParams(location.search).get("id")
            axios.get(`/api/clientLoans/${this.queryId}`)
                .then((res) => {
                    this.loan = res.data
                    this.idLoan = res.data.id
                    console.log(this.loan)
                }).catch((err) => console.log(err))
        },
        loanPayments() {
            console.log(this.idLoan, this.accountPayment)
            axios.post(`/api/loan/payments?id=${this.idLoan}&accountNumber=${this.accountPayment}`)
                .then((res) => {
                    this.errormessage = res.data
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        iconColor: 'grey',
                        title: `${this.errormessage}`,
                        showConfirmButton: false,
                        timer: 900,
                    })
                    setTimeout(() => {
                        location.href = "./accounts.html"
                    }, 1200)
                }).catch((err) => {
                    this.errormessage = err.response.data
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        iconColor: 'grey',
                        title: `${this.errormessage}`,
                        showConfirmButton: false,
                        timer: 900
                    })
                })
        },
        getaccounts() {
            axios.get("/api/clients/current/accounts")
                .then((res) => {
                    this.accounts = res.data.sort((a, b) => a.id - b.id)
                }).catch((err) => console.log(err))
        }
    },
}).mount('#app');