const app = Vue.createApp({
    data(){
        return {
            accountId: new URLSearchParams(window.location.search).get("id"),
            account: {},
            transactions: [],
            client: {}
        }
    },
    created(){
        this.loadData()
    },
    methods: {
        loadData(){
            let url1 = "/api/clients/current"
            let url2 = `/api/clients/current/accounts/${this.accountId}`
            const request1 = axios.get(url1)
            const request2 = axios.get(url2)
            axios.all([request1, request2]).then(axios.spread((...responses) => {
                this.client = responses[0].data
                this.account = responses[1].data
                if(responses[1].data.disabled === true){
                    window.location.replace("/web/accounts.html")
                }
                this.transactions = this.sortById(responses[1].data.transactions)
                this.filteredTransactions = this.sortById(responses[1].data.transactions)
            }))
            .catch(error =>{
                if(error.response.status === 500){
                    window.location.replace("/web/accounts.html")
                }
            })
        },
        sortById(transactionArray){
            transactionArray.sort((transactionA, transactionB) => {
                if(transactionA.id < transactionB.id){
                    return 1
                }
                if(transactionA.id > transactionB.id){
                    return -1
                }
                return 0
            })
            return transactionArray
        },
        logOut(){
            axios.post("/api/logout")
            .then(response => {
                window.location.replace("/web/index.html")
            })
        },
        getTransactionsPdf(){
            axios.post("/api/transactions/export/pdf", `id=${this.accountId}`, {responseType: 'blob'})
            .then(response=> {
                let file = response.headers['content-disposition']
                let fileName = decodeURI(file.substring(20))
                let link = document.createElement('a')
                link.href= URL.createObjectURL(response.data)
                link.download = fileName
                link.click()
                link.remove()
                setTimeout(()=>window.location.reload(), 3000)
            })
            .catch(error=> console.log(error.response.data))
        }
    }
})

app.mount("#app")