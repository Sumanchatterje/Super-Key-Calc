function generateAttributeInputs() {
    let numAttributes = document.getElementById("num_attributes").value;
    let attributeContainer = document.getElementById("attribute_inputs");
    attributeContainer.innerHTML = "";
    for (let i = 0; i < numAttributes; i++) {
        let input = document.createElement("input");
        input.type = "text";
        input.name = "attributes[]";
        input.placeholder = "Attribute " + (i + 1);
        input.required = true;
        input.style.marginTop = "2%";
        attributeContainer.appendChild(input);
        attributeContainer.appendChild(document.createElement("br"));
    }
}
function generateCandidateKeyInputs() {
    let numCandidateKeys = document.getElementById("num_candidate_keys").value;
    let candidateKeyContainer = document.getElementById("candidate_key_inputs");
    candidateKeyContainer.innerHTML = "";
    for (let i = 0; i < numCandidateKeys; i++) {
        let input = document.createElement("input");
        input.type = "text";
        input.name = "candidate_keys[]";
        input.placeholder = "Candidate Key " + (i + 1) + " (comma-separated)";
        input.required = true;
        input.style.marginTop = "2%";
        candidateKeyContainer.appendChild(input);
        candidateKeyContainer.appendChild(document.createElement("br"));
    }
}
    function clearFields(){
    location.reload();
    // document.getElementById("num_attributes").value="";
    // document.getElementById("num_candidate_keys").value="";
}
    const container = document.querySelector('.container');
    const loginBtn = document.querySelector('.login-btn');

function handleSubmit() {
    let formData = $("#superKeyForm").serialize(); // Collect form data

    // Extracting input values manually for backend payload
    let attributes = [];
    document.getElementsByName("attributes[]").forEach(input => {
        if (input.value.trim() !== "") attributes.push(input.value.trim());
    });

    let candidateKeys = [];
    document.getElementsByName("candidate_keys[]").forEach(input => {
        if (input.value.trim() !== "") candidateKeys.push(input.value.trim());
    });

    let functionalDependencies = [];
    document.querySelectorAll(".fd-wrapper").forEach(fdWrapper => {
        let lhs = fdWrapper.querySelector(".fd-input:first-child").value.trim();
        let rhs = fdWrapper.querySelector(".fd-input:last-child").value.trim();
        if (lhs !== "" && rhs !== "") {
            functionalDependencies.push({
                leftSide: lhs.split(",").map(attr => attr.trim()),
                rightSide: rhs.split(",").map(attr => attr.trim())
            });
        }
    });

    // Constructing JSON payload for backend
    let payload = {
        attributes: attributes,
        candidateKeys: candidateKeys,
        functionalDependencies: functionalDependencies
    };

    console.log("Sending Payload:", JSON.stringify(payload, null, 2));

    $.ajax({
        type: "POST",
        url: "https://super-key-spring-boot.onrender.com", // Update the backend URL
        contentType: "application/json", // Set correct content type
        data: JSON.stringify(payload), // Send as JSON string
        success: function(response) {
            console.log("Response from Backend:", response);
            $("#output").html(""); // Clear previous output

            response.forEach((superkey, index) => {
                $("#output").append(`<p><strong>${index + 1}:</strong> { ${superkey.join(", ")} }</p>`);
            });

            setTimeout(() => {
                container.classList.add('active');
            }, 500); // Delayed animation trigger
        },
        error: function(xhr, status, error) {
            console.error("Error:", xhr.responseText);
        }
    });

    const num_attributes = document.getElementById("num_attributes").value.trim();
    const num_candidate_keys = document.getElementById("num_candidate_keys").value.trim();

    if (num_attributes === "" || num_candidate_keys === "") {
        return;
    }

    console.log("Submitted Values:", num_attributes, num_candidate_keys);
}

loginBtn.addEventListener('click', () => {
    container.classList.remove('active');
});



function generateFDInputs() {
    let numFD = document.getElementById("num_fd").value;
    let fdContainer = document.getElementById("fd_inputs");
    fdContainer.innerHTML = ''; // Clear previous inputs

    for (let i = 1; i <= numFD; i++) {
        let fdWrapper = document.createElement("div");
        fdWrapper.classList.add("fd-wrapper");

        let leftInput = document.createElement("input");
        leftInput.type = "text";
        leftInput.classList.add("fd-input");
        leftInput.placeholder = "LHS"; // Left-hand side (determinant)
        leftInput.required = true;

        let arrow = document.createElement("span");
        arrow.classList.add("fd-arrow");
        arrow.innerHTML = "&#8658;"; // Unicode for right arrow (⇒)

        let rightInput = document.createElement("input");
        rightInput.type = "text";
        rightInput.classList.add("fd-input");
        rightInput.placeholder = "RHS"; // Right-hand side (dependent attributes)
        rightInput.required = true;

        fdWrapper.appendChild(leftInput);
        fdWrapper.appendChild(arrow);
        fdWrapper.appendChild(rightInput);

        fdContainer.appendChild(fdWrapper);
    }
}
function restrictInput() {
    let numCandidateKeys = document.getElementById("num_candidate_keys").value.trim();
    let numFDs = document.getElementById("num_fd").value.trim();

    if (numCandidateKeys !== "" && numFDs !== "") {
        alert("You can enter either Candidate Keys or Functional Dependencies, not both.");
        document.getElementById("num_candidate_keys").value = "";
        document.getElementById("num_fd").value = "";
        document.getElementById("candidate_key_inputs").innerHTML = "";
        document.getElementById("fd_inputs").innerHTML = "";
    }
}

// Attach the restriction function to both input fields
document.getElementById("num_candidate_keys").addEventListener("input", restrictInput);
document.getElementById("num_fd").addEventListener("input", restrictInput);

