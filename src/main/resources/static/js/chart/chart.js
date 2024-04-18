
let myCt = document.getElementById('myChart');

let myChart = new Chart(myCt, {
  type: 'bar',
  data: {
    labels: ['2020', '2021', '2022', '2023'],
    datasets: [
      {
        label: 'Dataset',
        data: [10,20,30,40],
      }
    ]
  },
});