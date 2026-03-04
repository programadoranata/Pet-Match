package ui;

import controller.AdocaoController;
import controller.AdotanteController;
import controller.PetController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Dimension;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Date;
import java.io.File;
import java.awt.Image;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.JSpinner.DateEditor;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import model.Adocao;
import model.Adotante;
import model.Pet;

public class MainFrame extends JFrame {
    private static final Color MAIN_BG = new Color(0xD3, 0xE5, 0xFF);
    private static final Color PANEL_BG = new Color(0xF2, 0xF7, 0xFF);
    private final PetController petController;
    private final AdotanteController adotanteController;
    private final AdocaoController adocaoController;

    private JTable petsTable;
    private DefaultTableModel petsModel;
    private JTextField petNomeField;
    private JTextField petEspecieField;
    private JTextField petIdadeField;
    private JTextField petStatusField;
    private JTextArea petDescricaoArea;

    private JTable adotantesTable;
    private DefaultTableModel adotantesModel;
    private Integer petIdSelecionado;
    private Integer adotanteIdSelecionado;
    private JTextField adotanteNomeField;
    private JTextField adotanteTelefoneField;
    private JTextField adotanteTipoField;

    private JTable adocoesTable;
    private DefaultTableModel adocoesModel;
    private Integer adocaoIdSelecionado;
    private Integer adocaoIdPetSelecionado;
    private Integer adocaoIdAdotanteSelecionado;
    private JTextField adocaoPetInfoField;
    private JTextField adocaoAdotanteInfoField;
    private JSpinner adocaoDataSpinner;

    private DefaultTableModel petSearchModel;
    private JTable petSearchTable;
    private JTextField petSearchField;

    private DefaultTableModel adotanteSearchModel;
    private JTable adotanteSearchTable;
    private JTextField adotanteSearchField;

    public MainFrame() {
        super("PetMatch - Gestão");
        this.petController = new PetController();
        this.adotanteController = new AdotanteController();
        this.adocaoController = new AdocaoController();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        getContentPane().setBackground(MAIN_BG);

        add(buildHeader(), BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Pets", buildPetsPanel());
        tabs.addTab("Adotantes", buildAdotantesPanel());
        tabs.addTab("Adoções", buildAdocoesPanel());
        tabs.setIconAt(0, loadIcon("love.png"));
        tabs.setIconAt(1, loadIcon("user.png"));
        tabs.setIconAt(2, loadIcon("dog-in-front-of-a-man.png"));

        add(tabs, BorderLayout.CENTER);

        carregarPets();
        carregarAdotantes();
        carregarAdocoes();
        buscarPets();
        buscarAdotantes();
    }

    private JPanel buildPetsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(MAIN_BG);

        petsModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Espécie", "Idade", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        petsTable = new JTable(petsModel);
        petsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        petsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                preencherPetFormulario();
            }
        });

        panel.add(new JScrollPane(petsTable), BorderLayout.CENTER);
        panel.add(buildPetForm(), BorderLayout.EAST);
        return panel;
    }

    private JPanel buildPetForm() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Cadastro de Pet"));
        form.setBackground(PANEL_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        form.add(new JLabel("Nome", SwingConstants.RIGHT), gbc);
        gbc.gridx = 1;
        petNomeField = new JTextField(18);
        form.add(petNomeField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Espécie", SwingConstants.RIGHT), gbc);
        gbc.gridx = 1;
        petEspecieField = new JTextField(18);
        form.add(petEspecieField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Idade", SwingConstants.RIGHT), gbc);
        gbc.gridx = 1;
        petIdadeField = new JTextField(5);
        form.add(petIdadeField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Status", SwingConstants.RIGHT), gbc);
        gbc.gridx = 1;
        petStatusField = new JTextField(18);
        form.add(petStatusField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        form.add(new JLabel("Descrição", SwingConstants.RIGHT), gbc);
        gbc.gridx = 1;
        gbc.weighty = 1;
        petDescricaoArea = new JTextArea(5, 18);
        form.add(new JScrollPane(petDescricaoArea), gbc);
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        form.add(buildPetButtons(), gbc);

        return form;
    }

    private JPanel buildPetButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        panel.setBackground(PANEL_BG);
        JButton novo = new JButton("Novo");
        JButton salvar = new JButton("Salvar");
        JButton excluir = new JButton("Excluir");
        novo.setIcon(loadIcon("plus.png", 16));
        salvar.setIcon(loadIcon("save.png", 16));
        excluir.setIcon(loadIcon("delete.png", 16));

        novo.addActionListener(e -> limparPetFormulario());
        salvar.addActionListener(e -> salvarPet());
        excluir.addActionListener(e -> excluirPet());

        panel.add(novo);
        panel.add(salvar);
        panel.add(excluir);
        return panel;
    }

    private JPanel buildAdotantesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(MAIN_BG);

        adotantesModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Telefone", "Tipo Preferido"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        adotantesTable = new JTable(adotantesModel);
        adotantesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        adotantesTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                preencherAdotanteFormulario();
            }
        });

        panel.add(new JScrollPane(adotantesTable), BorderLayout.CENTER);
        panel.add(buildAdotanteForm(), BorderLayout.EAST);
        return panel;
    }

    private JPanel buildAdotanteForm() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Cadastro de Adotante"));
        form.setBackground(PANEL_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        form.add(new JLabel("Nome", SwingConstants.RIGHT), gbc);
        gbc.gridx = 1;
        adotanteNomeField = new JTextField(18);
        form.add(adotanteNomeField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Telefone", SwingConstants.RIGHT), gbc);
        gbc.gridx = 1;
        adotanteTelefoneField = new JTextField(18);
        form.add(adotanteTelefoneField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Tipo Preferido", SwingConstants.RIGHT), gbc);
        gbc.gridx = 1;
        adotanteTipoField = new JTextField(18);
        form.add(adotanteTipoField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        form.add(buildAdotanteButtons(), gbc);

        return form;
    }

    private JPanel buildAdotanteButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        panel.setBackground(PANEL_BG);
        JButton novo = new JButton("Novo");
        JButton salvar = new JButton("Salvar");
        JButton excluir = new JButton("Excluir");
        novo.setIcon(loadIcon("plus.png", 16));
        salvar.setIcon(loadIcon("save.png", 16));
        excluir.setIcon(loadIcon("delete.png", 16));

        novo.addActionListener(e -> limparAdotanteFormulario());
        salvar.addActionListener(e -> salvarAdotante());
        excluir.addActionListener(e -> excluirAdotante());

        panel.add(novo);
        panel.add(salvar);
        panel.add(excluir);
        return panel;
    }

    private JPanel buildAdocoesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(MAIN_BG);

        adocoesModel = new DefaultTableModel(new Object[]{"ID", "ID Pet", "ID Adotante", "Data"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        adocoesTable = new JTable(adocoesModel);
        adocoesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        adocoesTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                preencherAdocaoFormulario();
            }
        });

        panel.add(new JScrollPane(adocoesTable), BorderLayout.CENTER);
        JScrollPane formScroll = new JScrollPane(buildAdocaoForm());
        formScroll.setBorder(null);
        formScroll.setPreferredSize(new Dimension(380, 0));
        panel.add(formScroll, BorderLayout.EAST);
        return panel;
    }

    private JPanel buildAdocaoForm() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Cadastro de Adoção"));
        form.setBackground(PANEL_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        form.add(buildAdocaoSearchPanel(), gbc);
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy++;
        form.add(new JLabel("Pet", SwingConstants.RIGHT), gbc);
        gbc.gridx = 1;
        adocaoPetInfoField = new JTextField(18);
        adocaoPetInfoField.setEditable(false);
        form.add(adocaoPetInfoField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Adotante", SwingConstants.RIGHT), gbc);
        gbc.gridx = 1;
        adocaoAdotanteInfoField = new JTextField(18);
        adocaoAdotanteInfoField.setEditable(false);
        form.add(adocaoAdotanteInfoField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Data", SwingConstants.RIGHT), gbc);
        gbc.gridx = 1;
        adocaoDataSpinner = new JSpinner(new SpinnerDateModel());
        DateEditor editor = new JSpinner.DateEditor(adocaoDataSpinner, "yyyy-MM-dd");
        adocaoDataSpinner.setEditor(editor);
        form.add(adocaoDataSpinner, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        form.add(buildAdocaoButtons(), gbc);

        return form;
    }

    private JPanel buildAdocaoSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(MAIN_BG);
        javax.swing.JSplitPane split = new javax.swing.JSplitPane(
                javax.swing.JSplitPane.VERTICAL_SPLIT,
                buildPetSearchPanel(),
                buildAdotanteSearchPanel()
        );
        split.setResizeWeight(0.5);
        split.setDividerLocation(0.5);
        split.setBorder(null);
        panel.add(split, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildPetSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout(6, 6));
        panel.setBorder(BorderFactory.createTitledBorder("Pesquisar Pet"));
        panel.setBackground(PANEL_BG);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        top.setBackground(PANEL_BG);
        top.add(new JLabel("Nome"));
        petSearchField = new JTextField(14);
        JButton buscar = new JButton("Buscar");
        buscar.addActionListener(e -> buscarPets());
        top.add(petSearchField);
        top.add(buscar);

        petSearchModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Espécie"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        petSearchTable = new JTable(petSearchModel);
        petSearchTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        petSearchTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = petSearchTable.getSelectedRow();
                if (row >= 0) {
                    adocaoIdPetSelecionado = (Integer) petSearchModel.getValueAt(row, 0);
                    String nome = String.valueOf(petSearchModel.getValueAt(row, 1));
                    String especie = String.valueOf(petSearchModel.getValueAt(row, 2));
                    adocaoPetInfoField.setText(nome + " (" + especie + ")");
                }
            }
        });

        JScrollPane scroll = new JScrollPane(petSearchTable);
        scroll.setPreferredSize(new Dimension(300, 120));

        panel.add(top, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildAdotanteSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout(6, 6));
        panel.setBorder(BorderFactory.createTitledBorder("Pesquisar Adotante"));
        panel.setBackground(PANEL_BG);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        top.setBackground(PANEL_BG);
        top.add(new JLabel("Nome"));
        adotanteSearchField = new JTextField(14);
        JButton buscar = new JButton("Buscar");
        buscar.addActionListener(e -> buscarAdotantes());
        top.add(adotanteSearchField);
        top.add(buscar);

        adotanteSearchModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Telefone"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        adotanteSearchTable = new JTable(adotanteSearchModel);
        adotanteSearchTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        adotanteSearchTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = adotanteSearchTable.getSelectedRow();
                if (row >= 0) {
                    adocaoIdAdotanteSelecionado = (Integer) adotanteSearchModel.getValueAt(row, 0);
                    String nome = String.valueOf(adotanteSearchModel.getValueAt(row, 1));
                    String telefone = String.valueOf(adotanteSearchModel.getValueAt(row, 2));
                    adocaoAdotanteInfoField.setText(nome + " (" + telefone + ")");
                }
            }
        });

        JScrollPane scroll = new JScrollPane(adotanteSearchTable);
        scroll.setPreferredSize(new Dimension(300, 120));

        panel.add(top, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildAdocaoButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        panel.setBackground(PANEL_BG);
        JButton novo = new JButton("Novo");
        JButton salvar = new JButton("Salvar");
        JButton excluir = new JButton("Excluir");
        novo.setIcon(loadIcon("plus.png", 16));
        salvar.setIcon(loadIcon("save.png", 16));
        excluir.setIcon(loadIcon("delete.png", 16));

        novo.addActionListener(e -> limparAdocaoFormulario());
        salvar.addActionListener(e -> salvarAdocao());
        excluir.addActionListener(e -> excluirAdocao());

        panel.add(novo);
        panel.add(salvar);
        panel.add(excluir);
        return panel;
    }

    private void carregarPets() {
        try {
            List<Pet> pets = petController.listarTodos();
            petsModel.setRowCount(0);
            for (Pet pet : pets) {
                petsModel.addRow(new Object[]{
                        pet.getIdPet(),
                        pet.getNome(),
                        pet.getEspecie(),
                        pet.getIdade(),
                        pet.getStatus()
                });
            }
        } catch (Exception ex) {
            showError("Erro ao carregar pets", ex);
        }
    }

    private void carregarAdotantes() {
        try {
            List<Adotante> adotantes = adotanteController.listarTodos();
            adotantesModel.setRowCount(0);
            for (Adotante adotante : adotantes) {
                adotantesModel.addRow(new Object[]{
                        adotante.getIdAdotante(),
                        adotante.getNome(),
                        adotante.getTelefone(),
                        adotante.getTipoPreferido()
                });
            }
        } catch (Exception ex) {
            showError("Erro ao carregar adotantes", ex);
        }
    }

    private void carregarAdocoes() {
        try {
            List<Adocao> adocoes = adocaoController.listarTodos();
            adocoesModel.setRowCount(0);
            for (Adocao adocao : adocoes) {
                adocoesModel.addRow(new Object[]{
                        adocao.getIdAdocao(),
                        adocao.getIdPet(),
                        adocao.getIdAdotante(),
                        adocao.getDataAdocao()
                });
            }
        } catch (Exception ex) {
            showError("Erro ao carregar adoções", ex);
        }
    }

    private void buscarPets() {
        String filtro = petSearchField.getText().trim();
        try {
            List<Pet> pets = petController.buscarPorNome(filtro);
            petSearchModel.setRowCount(0);
            for (Pet pet : pets) {
                petSearchModel.addRow(new Object[]{pet.getIdPet(), pet.getNome(), pet.getEspecie()});
            }
        } catch (Exception ex) {
            showError("Erro ao buscar pets", ex);
        }
    }

    private void buscarAdotantes() {
        String filtro = adotanteSearchField.getText().trim();
        try {
            List<Adotante> adotantes = adotanteController.buscarPorNome(filtro);
            adotanteSearchModel.setRowCount(0);
            for (Adotante adotante : adotantes) {
                adotanteSearchModel.addRow(new Object[]{adotante.getIdAdotante(), adotante.getNome(), adotante.getTelefone()});
            }
        } catch (Exception ex) {
            showError("Erro ao buscar adotantes", ex);
        }
    }

    private void preencherInfoAdocao() {
        if (adocaoIdPetSelecionado != null) {
            try {
                Pet pet = petController.buscarPorId(adocaoIdPetSelecionado);
                if (pet != null) {
                    adocaoPetInfoField.setText(pet.getNome() + " (" + pet.getEspecie() + ")");
                }
            } catch (Exception ex) {
                showError("Erro ao carregar pet da adoção", ex);
            }
        }
        if (adocaoIdAdotanteSelecionado != null) {
            try {
                Adotante adotante = adotanteController.buscarPorId(adocaoIdAdotanteSelecionado);
                if (adotante != null) {
                    adocaoAdotanteInfoField.setText(adotante.getNome() + " (" + adotante.getTelefone() + ")");
                }
            } catch (Exception ex) {
                showError("Erro ao carregar adotante da adoção", ex);
            }
        }
    }

    private void preencherPetFormulario() {
        int row = petsTable.getSelectedRow();
        if (row >= 0) {
            petIdSelecionado = (Integer) petsModel.getValueAt(row, 0);
            petNomeField.setText(String.valueOf(petsModel.getValueAt(row, 1)));
            petEspecieField.setText(String.valueOf(petsModel.getValueAt(row, 2)));
            petIdadeField.setText(String.valueOf(petsModel.getValueAt(row, 3)));
            petStatusField.setText(String.valueOf(petsModel.getValueAt(row, 4)));
            Pet pet = null;
            try {
                pet = petController.buscarPorId(petIdSelecionado);
            } catch (Exception ignored) {
            }
            if (pet != null) {
                petDescricaoArea.setText(pet.getDescricao());
            } else {
                petDescricaoArea.setText("");
            }
        }
    }

    private void preencherAdotanteFormulario() {
        int row = adotantesTable.getSelectedRow();
        if (row >= 0) {
            adotanteIdSelecionado = (Integer) adotantesModel.getValueAt(row, 0);
            adotanteNomeField.setText(String.valueOf(adotantesModel.getValueAt(row, 1)));
            adotanteTelefoneField.setText(String.valueOf(adotantesModel.getValueAt(row, 2)));
            adotanteTipoField.setText(String.valueOf(adotantesModel.getValueAt(row, 3)));
        }
    }

    private void preencherAdocaoFormulario() {
        int row = adocoesTable.getSelectedRow();
        if (row >= 0) {
            adocaoIdSelecionado = (Integer) adocoesModel.getValueAt(row, 0);
            adocaoIdPetSelecionado = (Integer) adocoesModel.getValueAt(row, 1);
            adocaoIdAdotanteSelecionado = (Integer) adocoesModel.getValueAt(row, 2);
            Object data = adocoesModel.getValueAt(row, 3);
            if (data instanceof LocalDate) {
                LocalDate ld = (LocalDate) data;
                Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
                adocaoDataSpinner.setValue(date);
            }
            preencherInfoAdocao();
        }
    }

    private void limparPetFormulario() {
        petIdSelecionado = null;
        petNomeField.setText("");
        petEspecieField.setText("");
        petIdadeField.setText("");
        petStatusField.setText("");
        petDescricaoArea.setText("");
        petsTable.clearSelection();
    }

    private void limparAdotanteFormulario() {
        adotanteIdSelecionado = null;
        adotanteNomeField.setText("");
        adotanteTelefoneField.setText("");
        adotanteTipoField.setText("");
        adotantesTable.clearSelection();
    }

    private void limparAdocaoFormulario() {
        adocaoIdSelecionado = null;
        adocaoIdPetSelecionado = null;
        adocaoIdAdotanteSelecionado = null;
        adocaoPetInfoField.setText("");
        adocaoAdotanteInfoField.setText("");
        adocaoDataSpinner.setValue(new Date());
        adocoesTable.clearSelection();
    }

    private void salvarPet() {
        try {
            Pet pet = new Pet();
            pet.setNome(petNomeField.getText().trim());
            pet.setEspecie(petEspecieField.getText().trim());
            pet.setIdade(parseIntOrZero(petIdadeField.getText()));
            pet.setStatus(petStatusField.getText().trim());
            pet.setDescricao(petDescricaoArea.getText().trim());

            if (petIdSelecionado == null) {
                petController.criar(pet);
            } else {
                pet.setIdPet(petIdSelecionado);
                petController.atualizar(pet);
            }
            limparPetFormulario();
            carregarPets();
        } catch (Exception ex) {
            showError("Erro ao salvar pet", ex);
        }
    }

    private void excluirPet() {
        if (petIdSelecionado == null) {
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Excluir pet selecionado?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                petController.remover(petIdSelecionado);
                limparPetFormulario();
                carregarPets();
            } catch (Exception ex) {
                showError("Erro ao excluir pet", ex);
            }
        }
    }

    private void salvarAdotante() {
        try {
            Adotante adotante = new Adotante();
            adotante.setNome(adotanteNomeField.getText().trim());
            adotante.setTelefone(adotanteTelefoneField.getText().trim());
            adotante.setTipoPreferido(adotanteTipoField.getText().trim());

            if (adotanteIdSelecionado == null) {
                adotanteController.criar(adotante);
            } else {
                adotante.setIdAdotante(adotanteIdSelecionado);
                adotanteController.atualizar(adotante);
            }
            limparAdotanteFormulario();
            carregarAdotantes();
        } catch (Exception ex) {
            showError("Erro ao salvar adotante", ex);
        }
    }

    private void excluirAdotante() {
        if (adotanteIdSelecionado == null) {
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Excluir adotante selecionado?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                adotanteController.remover(adotanteIdSelecionado);
                limparAdotanteFormulario();
                carregarAdotantes();
            } catch (Exception ex) {
                showError("Erro ao excluir adotante", ex);
            }
        }
    }

    private void salvarAdocao() {
        try {
            Adocao adocao = new Adocao();
            Integer idPet = adocaoIdPetSelecionado;
            Integer idAdotante = adocaoIdAdotanteSelecionado;

            if (idPet == null || idAdotante == null) {
                JOptionPane.showMessageDialog(this, "Selecione um Pet e um Adotante nas pesquisas.", "Validação", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!validarPetExiste(idPet) || !validarAdotanteExiste(idAdotante)) {
                return;
            }

            adocao.setIdPet(idPet);
            adocao.setIdAdotante(idAdotante);
            Date data = (Date) adocaoDataSpinner.getValue();
            if (data != null) {
                LocalDate localDate = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                adocao.setDataAdocao(localDate);
            }

            if (adocaoIdSelecionado == null) {
                adocaoController.criar(adocao);
            } else {
                adocao.setIdAdocao(adocaoIdSelecionado);
                adocaoController.atualizar(adocao);
            }
            limparAdocaoFormulario();
            carregarAdocoes();
        } catch (Exception ex) {
            showError("Erro ao salvar adoção", ex);
        }
    }

    private void excluirAdocao() {
        if (adocaoIdSelecionado == null) {
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Excluir adoção selecionada?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                adocaoController.remover(adocaoIdSelecionado);
                limparAdocaoFormulario();
                carregarAdocoes();
            } catch (Exception ex) {
                showError("Erro ao excluir adoção", ex);
            }
        }
    }

    private int parseIntOrZero(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (Exception ex) {
            return 0;
        }
    }

    private void showError(String title, Exception ex) {
        JOptionPane.showMessageDialog(this, title + "\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        header.setBackground(MAIN_BG);
        JLabel brand = new JLabel("PetMatch");
        brand.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 20));        brand.setIcon(loadIcon("pet-shop.png", 28));
        header.add(brand);
        return header;
    }

    private ImageIcon loadIcon(String fileName) {
        return loadIcon(fileName, 24);
    }

    private ImageIcon loadIcon(String fileName, int size) {
        File file = new File("assets", fileName);
        if (!file.exists()) {
            return null;
        }
        ImageIcon icon = new ImageIcon(file.getAbsolutePath());
        Image scaled = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    private boolean validarPetExiste(int idPet) {
        if (idPet <= 0) {
            JOptionPane.showMessageDialog(this, "Informe um ID de Pet válido.", "Validação", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            Pet pet = petController.buscarPorId(idPet);
            if (pet == null) {
                JOptionPane.showMessageDialog(this, "Pet não encontrado para o ID informado.", "Validação", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            return true;
        } catch (Exception ex) {
            showError("Erro ao validar pet", ex);
            return false;
        }
    }

    private boolean validarAdotanteExiste(int idAdotante) {
        if (idAdotante <= 0) {
            JOptionPane.showMessageDialog(this, "Informe um ID de Adotante válido.", "Validação", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            Adotante adotante = adotanteController.buscarPorId(idAdotante);
            if (adotante == null) {
                JOptionPane.showMessageDialog(this, "Adotante não encontrado para o ID informado.", "Validação", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            return true;
        } catch (Exception ex) {
            showError("Erro ao validar adotante", ex);
            return false;
        }
    }
}
