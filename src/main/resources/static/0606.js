async function getTableWithUsers() {
    let select = $('#editUser select');     //в html ищет этот тег и вставляет инфу в tbody
    select.empty();

    await userFetchService.findAllRoles()
        .then(res => res.json())
        .then(userDTOs => {
            userDTOs.forEach(user => {
                let arr = user.role;

                console.log(arr[0]);

                arr.map(role => {
                    console.log(`id: ${role.id}, name: ${role.name}`);

                    //html code
                    let tableFillingRole3 = `$(
                            <option>${role.name}</option>
                    )`;
                    $("#mesto").html(tableFillingRole3);
                });

            })
        })
}