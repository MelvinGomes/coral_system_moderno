// Pega a referência ao elemento principal onde o conteúdo será renderizado
const areaConteudo = document.getElementById('conteudo-principal');


// ===============================================
//        SEÇÃO DE GERENCIAMENTO DE CORISTAS (VERSÃO ESTÁVEL)
// ===============================================

// Função principal para carregar e exibir a seção de coristas
async function carregarConteudoCoristas() {
    // Redefine a área de conteúdo com o formulário e tabela simples
    areaConteudo.innerHTML = `
        <div id="lista-coristas">
            <h2>Lista de Coristas</h2>
            <table>
                <thead>
                    <tr>
                        <th>Nome</th>
                        <th>Tipo de Voz</th>
                        <th>Ativo</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody id="tabela-coristas-corpo"></tbody>
            </table>
        </div>

        <hr>
        <div id="form-edicao-corista" style="display:none; border: 1px solid #ccc; padding: 15px; margin-bottom: 20px;">
            <h2>Editar Corista</h2>
            <form onsubmit="atualizarCorista(event)">
                <input type="hidden" id="edit-id-corista">
                <input type="text" id="edit-nome-corista" placeholder="Nome completo" required>
                <input type="text" id="edit-tipovoz-corista" placeholder="Tipo de voz">
                <label><input type="checkbox" id="edit-ativo-corista"> Ativo</label>
                <br><br>
                <button type="submit">Salvar Alterações</button>
                <button type="button" onclick="esconderFormularioEdicaoCorista()">Cancelar</button>
            </form>
        </div>


        <div id="form-corista">
            <h2>Adicionar Novo Corista</h2>
            <form onsubmit="adicionarCorista(event)">
                <input type="text" id="nome-corista" placeholder="Nome completo" required>
                <input type="text" id="tipovoz-corista" placeholder="Tipo de voz">
                <button type="submit">Salvar</button>
            </form>
        </div>
    `;

    // Busca os dados e renderiza a tabela
    try {
        const coristas = await getCoristas();
        renderizarTabelaCoristas(coristas);
    } catch (error) {
        console.error("Falha ao carregar coristas:", error);
        document.getElementById('tabela-coristas-corpo').innerHTML = `<tr><td colspan="4">Erro ao carregar os dados.</td></tr>`;
    }
}

// Função para renderizar as linhas da tabela
function renderizarTabelaCoristas(coristas) {
    const corpoTabela = document.getElementById('tabela-coristas-corpo');
    corpoTabela.innerHTML = '';

    if (!Array.isArray(coristas)) {
        console.error("Dados de coristas não são um array:", coristas);
        return;
    }
    
    coristas.forEach(corista => {
        const linha = `
            <tr>
                <td>${corista.nome}</td>
                <td>${corista.tipoVoz || ''}</td>
                <td>${corista.ativo ? 'Sim' : 'Não'}</td>
                <td>
                    <button onclick="mostrarFormularioEdicaoCorista(${corista.id})">Editar</button>
                    <button onclick="excluirCorista(${corista.id})">Excluir</button>
                </td>
            </tr>
        `;
        corpoTabela.innerHTML += linha;
    });
}

// --- Funções de Comunicação com a API ---

// GET: Busca todos os coristas
async function getCoristas() {
    const response = await fetch('/api/coristas');
    if (!response.ok) {
        throw new Error('Erro ao buscar dados dos coristas.');
    }
    return await response.json();
}

// Função para mostrar o formulário de edição
async function mostrarFormularioEdicaoCorista(id) {
    try {
      
        const response = await fetch(`/api/coristas/${id}`);
        if (!response.ok) throw new Error('Corista não encontrado.');
        const corista = await response.json();

     
        document.getElementById('edit-id-corista').value = corista.id;
        document.getElementById('edit-nome-corista').value = corista.nome;
        document.getElementById('edit-tipovoz-corista').value = corista.tipoVoz;
        document.getElementById('edit-ativo-corista').checked = corista.ativo;

        
        document.getElementById('form-edicao-corista').style.display = 'block';
    } catch (error) {
        alert(error.message);
    }
}


function esconderFormularioEdicaoCorista() {
    document.getElementById('form-edicao-corista').style.display = 'none';
}

// POST: Adiciona um novo corista
async function adicionarCorista(event) {
    event.preventDefault();

    const novoCorista = {
        nome: document.getElementById('nome-corista').value,
        tipoVoz: document.getElementById('tipovoz-corista').value,
        ativo: true // Padrão
    };

    try {
        const response = await fetch('/api/coristas', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(novoCorista)
        });

        if (!response.ok) throw new Error('Falha ao salvar o corista.');

        alert('Corista salvo com sucesso!');
        carregarConteudoCoristas(); // Recarrega toda a seção
    } catch (error) {
        alert(error.message);
    }
}

// PUT: Atualiza um corista existente
async function atualizarCorista(event) {
    event.preventDefault();

    const id = document.getElementById('edit-id-corista').value;
    const coristaAtualizado = {
        nome: document.getElementById('edit-nome-corista').value,
        tipoVoz: document.getElementById('edit-tipovoz-corista').value,
        ativo: document.getElementById('edit-ativo-corista').checked
    };

    try {
        const response = await fetch(`/api/coristas/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(coristaAtualizado)
        });

        if (!response.ok) throw new Error('Falha ao atualizar o corista.');

        alert('Corista atualizado com sucesso!');
        carregarConteudoCoristas(); // Recarrega toda a seção para mostrar os dados novos
    } catch (error) {
        alert(error.message);
    }
}

// DELETE: Exclui um corista
async function excluirCorista(id) {
    if (!confirm('Tem certeza que deseja excluir este corista?')) {
        return;
    }

    try {
        const response = await fetch(`/api/coristas/${id}`, {
            method: 'DELETE'
        });

        if (!response.ok) throw new Error('Falha ao excluir o corista.');
        
        alert('Corista excluído com sucesso!');
        carregarConteudoCoristas(); // Recarrega a lista
    } catch (error) {
        alert(error.message);
    }
}

// ===============================================
//          SEÇÃO DE GERENCIAMENTO DE MÚSICOS
// ===============================================

// Função principal para carregar e exibir a seção de músicos (MODIFICADA)
async function carregarConteudoMusicos() {
    const musicos = await getMusicos();

    // Adicionado um formulário, assim como na seção de coristas
    areaConteudo.innerHTML = `
        <div id="lista-musicos">
            <h2>Lista de Músicos</h2>
            <table>
                <thead>
                    <tr>
                        <th>Nome</th>
                        <th>Instrumento</th>
                        <th>Ativo</th>
                        <th>Ações</th> <!-- Nova coluna -->
                    </tr>
                </thead>
                <tbody id="tabela-musicos-corpo"></tbody>
            </table>
        </div>
        <hr>
        <div id="form-musico">
            <h2>Adicionar Novo Músico</h2>
            <form onsubmit="adicionarMusico(event)">
                <input type="text" id="nome-musico" placeholder="Nome completo" required>
                <input type="text" id="instrumento-musico" placeholder="Instrumento">
                <button type="submit">Salvar</button>
            </form>
        </div>
    `;

    renderizarTabelaMusicos(musicos);
}

// Função para renderizar as linhas da tabela de músicos (MODIFICADA)
function renderizarTabelaMusicos(musicos) {
    const corpoTabela = document.getElementById('tabela-musicos-corpo');
    corpoTabela.innerHTML = ''; 

    musicos.forEach(musico => {
        // Adicionada a coluna de ações com o botão excluir
        const linha = `
            <tr>
                <td>${musico.nome}</td>
                <td>${musico.instrumento}</td>
                <td>${musico.ativo ? 'Sim' : 'Não'}</td>
                <td>
                    <button onclick="excluirMusico(${musico.id})">Excluir</button>
                </td>
            </tr>
        `;
        corpoTabela.innerHTML += linha;
    });
}

// --- Funções de Comunicação com a API ---

// GET: Busca todos os músicos (já existe)
async function getMusicos() {
    //... (código existente, sem alterações)
    try {
        const response = await fetch('/api/musicos');
        if (!response.ok) throw new Error('Erro ao buscar dados dos músicos.');
        return await response.json();
    } catch (error) {
        console.error('Falha na requisição GET para músicos:', error);
        return [];
    }
}

// ========== ADICIONE AS FUNÇÕES ABAIXO ==========

// POST: Adiciona um novo músico
async function adicionarMusico(event) {
    event.preventDefault();

    const nome = document.getElementById('nome-musico').value;
    const instrumento = document.getElementById('instrumento-musico').value;

    const novoMusico = {
        nome: nome,
        instrumento: instrumento,
        ativo: true // Por padrão, novos músicos são ativos
    };

    try {
        const response = await fetch('/api/musicos', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(novoMusico)
        });

        if (!response.ok) throw new Error('Erro ao salvar músico.');

        alert('Músico salvo com sucesso!');
        carregarConteudoMusicos(); // Recarrega a seção para mostrar o novo registro
    } catch (error) {
        console.error('Falha na requisição POST para músicos:', error);
        alert('Falha ao salvar. Verifique o console.');
    }
}

// DELETE: Exclui um músico
async function excluirMusico(id) {
    if (!confirm('Tem certeza que deseja excluir este músico?')) {
        return;
    }

    try {
        const response = await fetch(`/api/musicos/${id}`, {
            method: 'DELETE'
        });

        if (!response.ok) throw new Error('Erro ao excluir músico.');

        alert('Músico excluído com sucesso!');
        carregarConteudoMusicos(); // Recarrega para remover da lista
    } catch (error) {
        console.error('Falha na requisição DELETE para músicos:', error);
        alert('Falha ao excluir. Verifique o console.');
    }
}

// ===============================================
//        SEÇÃO DE GERENCIAMENTO DA AGENDA
// ===============================================

// Função principal para carregar e exibir a seção da agenda
async function carregarConteudoAgenda() {
    const eventos = await getAgenda();

    areaConteudo.innerHTML = `
        <div id="lista-agenda">
            <h2>Próximos Eventos</h2>
            <table>
                <thead>
                    <tr>
                        <th>Data</th>
                        <th>Local</th>
                        <th>Descrição</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody id="tabela-agenda-corpo"></tbody>
            </table>
        </div>
        <hr>
        <div id="form-agenda">
            <h2>Adicionar Novo Evento</h2>
            <form onsubmit="adicionarEvento(event)">
                <input type="date" id="data-agenda" required>
                <input type="text" id="local-agenda" placeholder="Local do evento" required>
                <input type="text" id="descricao-agenda" placeholder="Descrição">
                <button type="submit">Salvar</button>
            </form>
        </div>
    `;

    renderizarTabelaAgenda(eventos);
}

// Função para renderizar as linhas da tabela da agenda (MODIFICADA)
function renderizarTabelaAgenda(eventos) {
    const corpoTabela = document.getElementById('tabela-agenda-corpo');
    corpoTabela.innerHTML = ''; 

    eventos.sort((a, b) => new Date(b.data) - new Date(a.data));

    eventos.forEach(evento => {
        const dataFormatada = new Date(evento.data).toLocaleDateString('pt-BR', { timeZone: 'UTC' });

        const linha = `
            <tr>
                <td>${dataFormatada}</td>
                <td>${evento.local}</td>
                <td>${evento.descricao}</td>
                <td>
                    <!-- O evento.local é passado como string para ser exibido no título -->
                    <button onclick="carregarGerenciamentoPresencas(${evento.id}, '${evento.local}')">Presenças</button>
                    <button onclick="excluirEvento(${evento.id})">Excluir</button>
                </td>
            </tr>
        `;
        corpoTabela.innerHTML += linha;
    });
}


// --- Funções de Comunicação com a API ---

// GET: Busca todos os eventos da agenda
async function getAgenda() {
    try {
        const response = await fetch('/api/agenda');
        if (!response.ok) throw new Error('Erro ao buscar eventos da agenda.');
        return await response.json();
    } catch (error) {
        console.error('Falha na requisição GET para agenda:', error);
        return [];
    }
}

// POST: Adiciona um novo evento
async function adicionarEvento(event) {
    event.preventDefault();

    const novoEvento = {
        data: document.getElementById('data-agenda').value,
        local: document.getElementById('local-agenda').value,
        descricao: document.getElementById('descricao-agenda').value,
    };

    try {
        const response = await fetch('/api/agenda', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(novoEvento)
        });

        if (!response.ok) throw new Error('Erro ao salvar evento.');

        alert('Evento salvo com sucesso!');
        carregarConteudoAgenda(); // Recarrega a seção
    } catch (error) {
        console.error('Falha na requisição POST para agenda:', error);
        alert('Falha ao salvar evento. Verifique o console.');
    }
}

// DELETE: Exclui um evento
async function excluirEvento(id) {
    if (!confirm('Tem certeza que deseja excluir este evento?')) {
        return;
    }

    try {
        const response = await fetch(`/api/agenda/${id}`, {
            method: 'DELETE'
        });

        if (!response.ok) throw new Error('Erro ao excluir evento.');

        alert('Evento excluído com sucesso!');
        carregarConteudoAgenda(); // Recarrega para remover da lista
    } catch (error) {
        console.error('Falha na requisição DELETE para agenda:', error);
        alert('Falha ao excluir evento. Verifique o console.');
    }
}

// ===============================================
//        SEÇÃO DE GERENCIAMENTO DE PRESENÇAS (MODIFICADA)
// ===============================================

async function carregarGerenciamentoPresencas(idAgenda, localEvento) {
    areaConteudo.innerHTML = `<h2>Carregando presenças para: ${localEvento}...</h2>`;

    try {
        // Agora busca coristas, músicos e presenças, tudo em paralelo
        const [coristas, musicos, presencasAtuais] = await Promise.all([
            fetch('/api/coristas').then(res => res.json()),
            fetch('/api/musicos').then(res => res.json()),
            fetch(`/api/agenda/${idAgenda}/presencas`).then(res => res.json())
        ]);

        // Cria listas de HTML separadas para coristas e músicos
        const htmlCoristas = coristas.map(corista => 
            criarCheckboxParticipante(corista, 'CORISTA', presencasAtuais)
        ).join('');
        
        const htmlMusicos = musicos.map(musico =>
            criarCheckboxParticipante(musico, 'MUSICO', presencasAtuais)
        ).join('');

        areaConteudo.innerHTML = `
            <h2>Gerenciar Presenças - ${localEvento}</h2>
            <form onsubmit="salvarPresencas(event, ${idAgenda})">
                <h3>Coristas</h3>
                <div id="lista-presenca-coristas">
                    ${htmlCoristas}
                </div>
                <hr>
                <h3>Músicos</h3>
                <div id="lista-presenca-musicos">
                    ${htmlMusicos}
                </div>
                <hr>
                <button type="submit">Salvar Presenças</button>
                <button type="button" onclick="carregarConteudoAgenda()">Voltar para Agenda</button>
            </form>
        `;

    } catch (error) {
        console.error("Erro ao carregar gerenciamento de presenças:", error);
        areaConteudo.innerHTML = `<p>Ocorreu um erro. Tente novamente.</p>`;
    }
}

// Função auxiliar para evitar repetição de código
function criarCheckboxParticipante(participante, tipo, presencasAtuais) {
    const presenca = presencasAtuais.find(p => 
        p.idParticipante === participante.id && p.tipoParticipante === tipo
    );
    const isChecked = presenca ? presenca.presente : false;
    
    // Armazena tanto o ID quanto o TIPO nos atributos data-*
    return `
        <div>
            <input type="checkbox" id="${tipo}-${participante.id}" 
                   data-participante-id="${participante.id}" 
                   data-tipo-participante="${tipo}" ${isChecked ? 'checked' : ''}>
            <label for="${tipo}-${participante.id}">${participante.nome}</label>
        </div>
    `;
}

async function salvarPresencas(event, idAgenda) {
    event.preventDefault();

    // Seleciona TODOS os checkboxes das duas listas
    const checkboxes = document.querySelectorAll('#lista-presenca-coristas input, #lista-presenca-musicos input');
    
    // Mapeia o estado de cada checkbox para o formato que a API espera, agora incluindo o tipo
    const payload = Array.from(checkboxes).map(cb => ({
        idParticipante: parseInt(cb.dataset.participanteId),
        tipoParticipante: cb.dataset.tipoParticipante,
        presente: cb.checked
    }));

    try {
        const response = await fetch(`/api/agenda/${idAgenda}/presencas`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (!response.ok) throw new Error('Erro ao salvar presenças.');

        alert('Presenças salvas com sucesso!');
        carregarConteudoAgenda(); // Volta para a tela da agenda
    } catch (error) {
        console.error('Falha ao salvar presenças:', error);
        alert('Falha ao salvar. Verifique o console.');
    }
}