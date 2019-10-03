const HEIM_SELECTIONS_SELECTOR = '.heim-spieler-selection';
const GAST_SELECTIONS_SELECTOR = '.gast-spieler-selection';
const HEIM_TRIGGER_SELECTOR = '#heimteam';
const GAST_TRIGGER_SELECTOR = '#gastteam';

const REST_URL = '/team/{uuid}';

const d = document;
const $  = d.querySelector.bind(d);
const $$ = d.querySelectorAll.bind(d);

window.onload = () => {
    console.log("js onload");
    registerEventListeners();
    filterOptionsOnInit();
};

function registerEventListeners() {
    $(HEIM_TRIGGER_SELECTOR).addEventListener('change', (event) => {
        const uuid = event.target.value;
        console.log("Heimteam changed: ", event);
        getTeamByUuid('heim', uuid, processTeamDto);
    });
    $(GAST_TRIGGER_SELECTOR).addEventListener('change', (event) => {
        const uuid = event.target.value;
        console.log("Gastteam changed: ", event);
        getTeamByUuid('gast', uuid, processTeamDto);
    });
}

/**
 * Holt ein Team-Dto vom Server anhand seiner UUID um die Spieler des Teams zu finden.
 *
 * @param team 'heim' oder 'gast'
 * @param uuid UUID
 * @param callback Callback, an das das Team und das Dto gereicht werden
 */
function getTeamByUuid(team, uuid, callback) {
    fetch(REST_URL.replace('{uuid}', uuid))
        .then(res => res.json())
        .catch(err => console.error('Konnte Spieler nicht abrufen', err))
        .then(dto => callback(team, dto))
        .catch(err => console.error('Fehler beim Verarbeiten der Spieler', err));
}

function processTeamDto(team, dto) {
    const selects = team === 'heim' ? $$(HEIM_SELECTIONS_SELECTOR) : $$(GAST_SELECTIONS_SELECTOR);
    let optionsHtml = '<option value="">-</option>'; // default option
    dto.spieler.forEach(spieler => {
       optionsHtml += `<option value="${spieler.id}">${spieler.fullName}</option>`;
    });
    selects.forEach(select => {
       select.innerHTML = optionsHtml;
    });
}

/**
 * Wenn die Seite "initiert" wird, müssen für das ausgewählte Team die Optionen die bereits ausgewählt sind
 * erhalten bleiben, alle anderen aber gelöscht.
 */
function filterOptionsOnInit() {
    const preselectedHomeValue = $(HEIM_TRIGGER_SELECTOR).value;
    const preselectedGastValue = $(GAST_TRIGGER_SELECTOR).value;

    if (preselectedHomeValue) {
        getTeamByUuid('heim', preselectedHomeValue, filterOptions);
    }

    if (preselectedGastValue) {
        getTeamByUuid('gast', preselectedGastValue, filterOptions);

    }
}

/**
 * Beim initialisieren der Seite werden nun für ein Team alle Options der entsprechenden Selects gefiltert:
 * alle die nicht dem Team angehören (standardmäßig sind alle in der Auswahl vom Server) werden heraus gefiltert:
 * Machen kein einfaches ersetzen, da wir options die "selected" sind behalten wollen
 * @param team
 * @param dto
 */
function filterOptions(team, dto) {
    const selects = team === 'heim' ? $$(HEIM_SELECTIONS_SELECTOR) : $$(GAST_SELECTIONS_SELECTOR);
    const spielerIds = dto.spieler.map(spieler => spieler.id); // Array von ID
    selects.forEach(select => {
        const options = select.querySelectorAll('option'); // Alle Options einer bestimmten Select-Box
        for (let i = 0; i < options.length; i++) {
            if (options[i].value === '') continue; // das leere option feld auch erlauben
            if (!spielerIds.includes(options[i].value)) {
                console.log(`Spieler ${options[i].value} ist NICHT in dem Team; fliegt aus der NodeList`);
                select.removeChild(options[i]);
            } else {
                console.log(`Spieler ${options[i].value} ist in dem Team; bleibt in der NodeList`);
                // vom Server steht im InnerHTML das Proeprty "fullNameWithTeamPrefix"; das können wir hier ersetzen
                options[i].innerHTML = dto.spieler.find(spieler => spieler.id === options[i].value).fullName;
            }
        }
    });
}